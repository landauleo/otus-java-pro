package ru.otus.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;

public class HashHelper {

    private static final byte[] SALT = "ВОТ ЭТО СОЛЬ".getBytes(StandardCharsets.UTF_8);
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static boolean doesPasswordsMatch(String passFromRequest, String passFromDb) {
        String hashedPassFromRequest = getHashedPass(passFromRequest);

        return hashedPassFromRequest.equals(passFromDb);
    }

    public static String getHashedPass(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new InputMismatchException("SHA-512 algorithm not found, something went wrong during passwords comparison");
        }
         return bytesToHex(md.digest(password.getBytes(StandardCharsets.UTF_8)));
    }

    private static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
