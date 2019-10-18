package prova.prova.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import prova.prova.dtos.UserDto;
import prova.prova.enums.RoleEnum;
import prova.prova.models.User;
import prova.prova.response.Response;
import prova.prova.services.UserService;
import prova.prova.utils.PasswordUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Camila Siqueira
 */

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * List all users registered
     *
     * @return List<UserDto>
     */
    @GetMapping(value = "")
    public ResponseEntity<Response<List<UserDto>>> findAll() {
        log.info("Finding all users");

        List<UserDto> userDtoList = userService.findAll().stream()
                .map(user -> this.convertUserFromUserDto(user)).collect(Collectors.toList());

        Response<List<UserDto>> response = new Response<>();
        response.setData(userDtoList);

        return ResponseEntity.ok(response);
    }

    /**
     * Get a user by received id
     *
     * @param id's user
     * @return user data
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<UserDto>> findUserById(@PathVariable("id") String id) {
        log.info("Searching user by id", id);

        Response<UserDto> response = new Response<>();
        Optional<User> userOp = userService.findById(id);

        if (userOp.isPresent()) {
            response.setData(convertUserFromUserDto(userOp.get()));
            return ResponseEntity.ok(response);
        }

        log.info("User not found", id);
        response.getErrors().add("User not found: " + id);
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Get a user by received email
     *
     * @param email's user
     * @return user data
     */
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<Response<UserDto>> findUserByEmail(@PathVariable("email") String email) {
        log.info("Finding user by email", email);

        Response<UserDto> response = new Response<>();
        Optional<User> userOp = userService.findByEmail(email);

        if (userOp.isPresent()) {
            response.setData(convertUserFromUserDto(userOp.get()));
            return ResponseEntity.ok(response);
        }

        log.info("User not found", email);
        response.getErrors().add("User not found: " + email);
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Register a user.
     * Only for admin role.
     *
     * @param userDto
     * @param result
     * @return registered user
     */
    @PostMapping(value = "/save")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<UserDto>> save(@Valid @RequestBody UserDto userDto, BindingResult result) {
        log.info("Saving user", userDto);

        Response<UserDto> response = new Response<>();
        validateExistingData(userDto, result);
        User user = convertUserDtoFromUser(userDto, result);

        if (result.hasErrors()) {
            log.info("Not possible save user. Error: ", result.getAllErrors());

            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        user.setPassword(PasswordUtils.generateBCrypt(user.getPassword()));
        response.setData(convertUserFromUserDto(userService.save(user)));
        return ResponseEntity.ok(response);
    }

    /**
     * Update a user.
     * Only for admin role.
     *
     * @param userDto
     * @param result
     * @return updated user
     */
    @PutMapping(value = "/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<UserDto>> update(@Valid @RequestBody UserDto userDto, BindingResult result) {
        log.info("Updating user", userDto);

        Response<UserDto> response = new Response<>();
        validateUser(userDto, result);
        User user = convertUserDtoFromUser(userDto, result);

        if (result.hasErrors()) {
            log.info("Not possible update user. Error: ", result.getAllErrors());

            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(convertUserFromUserDto(userService.save(user)));
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a user by id.
     * Only for admin role.
     *
     * @param id
     * @return ok if the user was deleted
     */
    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> deleteById(@PathVariable("id") String id) {
        log.info("Deleting user", id);

        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        log.info("Not possible delete user. Invalid ID ", id);
        Response<String> response = new Response<>();
        response.getErrors().add("Not possible delete user. Invalid ID : " + id);
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Verify if new user data is not registered for another user.
     * Include errors if the data already exists in database.
     *
     * @param user
     * @param result
     */
    private void validateExistingData(UserDto user, BindingResult result) {
        this.userService.findByCpf(user.getCpf())
                .ifPresent(func -> result.addError(new ObjectError("cpf", "CPF já cadastrado.")));

        this.userService.findByEmail(user.getEmail())
                .ifPresent(func -> result.addError(new ObjectError("email", "Email já cadastrado.")));
    }

    /**
     * Validate user for updating, encrypting password if it's changed.
     *
     * @param user
     * @param result
     */
    private void validateUser(UserDto user, BindingResult result) {
        if (user.getId() == null || user.getId().isEmpty()) {
            result.addError(new ObjectError("user", "User not informed.."));
            return;
        }

        log.info("Validating user id {}: ", user.getId());
        Optional<User> userOP = this.userService.findById(user.getId());

        if (!userOP.isPresent()) {
            result.addError(new ObjectError("user", "User not found."));
        }

        //Encrypt the password if is different
        if (!userOP.get().getPassword().equals(user.getPassword())) {
            user.setPassword(PasswordUtils.generateBCrypt(user.getPassword()));
        }
    }

    /**
     * Converts UserDto in User.
     *
     * @param userDto to be converted in user
     * @param result
     * @return user
     */
    private User convertUserDtoFromUser(UserDto userDto, BindingResult result) {
        if (userDto.getRole() != null) {
            userDto.setRole(userDto.getRole().toUpperCase());
            if (ObjectUtils.containsConstant(RoleEnum.values(), userDto.getRole())) {
                return new User(userDto.getId(), userDto.getEmail(), userDto.getName(), userDto.getPassword(),
                        userDto.getCpf(), userDto.getTel(), userDto.getRole());
            }
        }

        result.addError(new ObjectError("role", "Invalid Role"));
        return null;
    }

    /**
     * Converts User in UserDto.
     *
     * @param user to be converted in userDto
     * @return userDto
     */
    private UserDto convertUserFromUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName(), user.getPassword(),
                user.getCpf(), user.getTel(), user.getRole().name());
    }
}
