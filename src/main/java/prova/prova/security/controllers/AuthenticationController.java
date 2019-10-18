package prova.prova.security.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import prova.prova.models.User;
import prova.prova.response.Response;
import prova.prova.security.dto.JwtAuthenticationDto;
import prova.prova.security.dto.TokenDto;
import prova.prova.security.utils.JwtTokenUtil;
import prova.prova.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private static final String TOKEN_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    /**
     * Generate a new JWT Token
     *
     * @param authenticationDto
     * @param result
     * @return ResponseEntity<Response < TokenDto>>
     * @throws AuthenticationException
     */

    @PostMapping
    public ResponseEntity<Response<TokenDto>> generateJwtToken(@Valid @RequestBody JwtAuthenticationDto authenticationDto
            , BindingResult result) throws AuthenticationException {
        Response<TokenDto> response = new Response<TokenDto>();

        if (result.hasErrors()) {
            log.error("Error when validate: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors()
                    .add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        if (!isValid(authenticationDto.getEmail())) {
            Optional<User> userOP = userService.findById(authenticationDto.getEmail());

            if (userOP.isPresent()) {
                authenticationDto.setEmail(userOP.get().getEmail());
                authenticationDto.setId(userOP.get().getId());
            }
        }

        log.info("Generating token for email: {}.", authenticationDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.getEmail(),
                        authenticationDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
        String token = jwtTokenUtil.getToken(userDetails);
        response.setData(new TokenDto(token));
        return ResponseEntity.ok(response);
    }

    /**
     * Generate a new toke with a new expiration date
     *
     * @param request
     * @return ResponseEntity<Response < TokenDto>>
     */
    @PostMapping(value = "/refresh")
    public ResponseEntity<Response<TokenDto>> generateJwtTokenRefresh(HttpServletRequest request) {
        log.info("Genarating JWT token refresh.");
        Response<TokenDto> response = new Response<TokenDto>();
        Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

        if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
            token = Optional.of(token.get().substring(7));
        }
        if (!token.isPresent()) {
            response.getErrors().add("Token not informed.");
        } else if (!jwtTokenUtil.isTokenValid(token.get())) {
            response.getErrors().add("Token invalid or expired.");
        }
        if (!response.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(response);
        }
        String refreshedToken = jwtTokenUtil.refreshToken(token.get());
        response.setData(new TokenDto(refreshedToken));
        return ResponseEntity.ok(response);
    }

    private boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }

        return pat.matcher(email).matches();
    }
}
