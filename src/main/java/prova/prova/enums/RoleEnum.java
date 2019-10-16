package prova.prova.enums;

/**
 * Enum represents the role of one user.
 *
 * @author Camila Siqueira
 */

public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private String name;

    private RoleEnum(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
