package prova.prova.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Camila Siqueira
 */
public class PasswordUtils {
    public static String generateBCrypt(String password) {
        if (password != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(password);
        }

        return null;
    }

    public static boolean verifyPassword(String password, String passwordEncoded) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, passwordEncoded);
    }
}
