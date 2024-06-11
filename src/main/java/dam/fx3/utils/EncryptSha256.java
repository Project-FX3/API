package dam.fx3.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class EncryptSha256 {

    public static String encrypt(String passwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(passwd.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
