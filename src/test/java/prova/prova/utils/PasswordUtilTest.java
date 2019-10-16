package prova.prova.utils;

import org.junit.Assert;
import org.junit.Test;

public class PasswordUtilTest {
    private String PASSWORD = "123456";

    @Test
    public void testNullPassword() throws Exception {
        Assert.assertNull(PasswordUtils.generateBCrypt(null));
    }

    @Test
    public void testVerifyEncryptedPassword() throws Exception {
        String hash = PasswordUtils.generateBCrypt(PASSWORD);

        Assert.assertTrue(PasswordUtils.verifyPassword(PASSWORD, hash));
    }
}
