package prova.prova.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * @author Camila Siqueira
 */

@Document("User")
public class User {

    public enum Role {
        ADMIN,
        USER;
    }

    @Id
    private String id;
    @Indexed
    private String email;
    private String name;
    private String password;
    private long cpf;
    private long tel;
    private Role role;

    public User(String id, String email, String name, String password, long cpf, long tel, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.cpf = cpf;
        this.tel = tel;
        this.role = role;
    }

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

    public long getCpf() {
        return cpf;
    }

    public long getTel() {
        return tel;
    }

    public Role getRole() {
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

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
