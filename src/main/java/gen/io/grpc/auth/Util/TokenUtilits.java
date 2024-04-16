package gen.io.grpc.auth.Util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenUtilits {  //  Хеширование SHA-256
    private static final String SALT = "my-secret-salt";

    public static String encrypt(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(SALT.getBytes());
        byte[] bytes = md.digest(str.getBytes());
        return new String(Base64.encodeBase64(bytes));
    }

    public static String decrypt(String str) throws NoSuchAlgorithmException {
        byte[] bytes = Base64.decodeBase64(str);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(SALT.getBytes());
        byte[] decryptedBytes = md.digest(bytes);
        return new String(decryptedBytes);
    }
}
