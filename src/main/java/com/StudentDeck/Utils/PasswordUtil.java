package com.StudentDeck.Utils;

/**
 * Code adapted from: https://stackoverflow.com/questions/10303767/encrypt-and-decrypt-in-java#:~:text=Encode%20Text%20%3A%20For%20consistency%20across,key%20and%20decrypt%20the%20bytes.
 * Line 26 - 45
 * Modifications:
 *  - Saved generated key as a String constant,
 *  - Refactored code to conform to software engineering best practices.
 *  - Implemented class as a singleton
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PasswordUtil {
    private static final String KEY = "uvvn+WqfgCFH51smMk7rDg==";
    public static PasswordUtil instance;
    private static Cipher cipher;

    private PasswordUtil() {
    }

    private static SecretKey getKey() {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedKey = decoder.decode(KEY);
        SecretKey decodedSK = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return decodedSK;
    }

    public String encrypt(String password) {
        String encryptedPassword = null;
        try {
            cipher = Cipher.getInstance("AES");
            byte[] plainTextByte = password.getBytes();
            SecretKey secretKey = getKey();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = cipher.doFinal(plainTextByte);
            Base64.Encoder encoder = Base64.getEncoder();
             encryptedPassword = encoder.encodeToString(encryptedByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert encryptedPassword != null;
        return encryptedPassword;
    }

    public String decrypt(String encryptedPassword){
        String decryptedPassword = null;
        try {
            cipher = Cipher.getInstance("AES");
            SecretKey secretKey = getKey();
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedTextByte = decoder.decode(encryptedPassword);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
            decryptedPassword = new String(decryptedByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert decryptedPassword != null;
        return decryptedPassword;
    }

    public static PasswordUtil getInstance() {
        if (instance == null) {
            instance = new PasswordUtil();
        }
        return instance;
    }
}