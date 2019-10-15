package prova.prova.enums;

/**
 * @author Camila Siqueira
 */
public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private String name;

    private RoleEnum(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
