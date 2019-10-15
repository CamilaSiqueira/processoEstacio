package prova.prova.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import prova.prova.enums.RoleEnum;

import javax.persistence.Id;

/**
 * @author Camila Siqueira
 */

@Document("user")
public class User {

    private String id;
    @Indexed
    private String email;
    private String name;
    private String password;
    private String cpf;
    private String tel;
    private RoleEnum role;

    public User() {

    }

    public User(String id, String email, String name, String password, String cpf, String tel, RoleEnum role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.cpf = cpf;
        this.tel = tel;
        this.role = role;
    }

    public User(String id, String email, String name, String password, String cpf, String tel, String role) {
        this(id, email, name, password, cpf, tel, RoleEnum.valueOf(role));
    }

    @Id
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTel() {
        return tel;
    }

    public RoleEnum getRole() {
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

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User: {" +
                "id:" + getId() +
                ", email:" + getEmail();
    }
}
