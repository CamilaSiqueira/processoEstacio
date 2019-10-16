package prova.prova.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import prova.prova.utils.constraint.RoleCheck;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author Camila Siqueira
 */

public class UserDto {

    private String id;
    private String email;
    private String name;
    private String password;
    private String cpf;
    private String tel;
    private String role;

    public UserDto(String id, String email, String name, String password, String cpf, String tel, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.cpf = cpf;
        this.tel = tel;
        this.role = role;
    }

    @NotEmpty(message = "ID deve ser preenchido.")
    public String getId() {
        return id;
    }

    @NotEmpty(message = "E-mail deve ser preenchido.")
    @Email(message = "E-mail inválido.")
    public String getEmail() {
        return email;
    }

    @NotEmpty(message = "Nome deve ser preenchido.")
    public String getName() {
        return name;
    }

    @NotEmpty(message = "Senha deve ser preenchida.")
    public String getPassword() {
        return password;
    }

    @NotEmpty(message = "CPF deve ser preenchido.")
    @CPF(message = "CPF inválido.")
    public String getCpf() {
        return cpf;
    }

    @NotEmpty(message = "Telefone deve ser preenchido.")
    @Length(min = 11, max = 11, message = "Telefone deve conter DDD999999999.")
    @Digits(integer = 11, fraction = 0, message = "Telefone deve conter apenas valores numéricos.")
    public String getTel() {
        return tel;
    }

    @RoleCheck(message = "Tipo de usuário inválido.")
    public String getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDto: {" +
                "id:" + getId() +
                ", email:" + getEmail();
    }
}
