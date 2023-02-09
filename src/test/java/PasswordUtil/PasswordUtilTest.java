package PasswordUtil;
import com.StudentDeck.Utils.PasswordUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PasswordUtilTest {
    PasswordUtil passwordUtil;

    @Test
    void encryptTest() {
        String passwordToEncrypt = "Password@1";
        passwordUtil = PasswordUtil.getInstance();
        String encryptedPassword = passwordUtil.encrypt(passwordToEncrypt);
        assertEquals(passwordUtil.decrypt(encryptedPassword), passwordToEncrypt);
    }

    @Test
    void decryptPassword() {
        passwordUtil = PasswordUtil.getInstance();
        String passwordToEncrypt = "Password@1";
        String encryptedPassword = passwordUtil.encrypt(passwordToEncrypt);
        String decryptedPassword = passwordUtil.decrypt(encryptedPassword);
        assertEquals(passwordToEncrypt, decryptedPassword);
    }
}
