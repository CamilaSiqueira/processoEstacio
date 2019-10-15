package prova.prova.security.dto;

import prova.prova.utils.constraint.IdCheck;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@IdCheck(message = "Some identification must be given")
public class JwtAuthenticationDto {

    private String id;
    private String email;
    private String password;

    public JwtAuthenticationDto() {
    }

    @Email(message = "Invalid email.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password can't be empty")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

