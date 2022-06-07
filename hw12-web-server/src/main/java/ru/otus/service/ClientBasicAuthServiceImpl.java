package ru.otus.service;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClientBasicAuthServiceImpl implements BasicAuthService{
    private static final byte[] SALT = "ВОТ ЭТО СОЛЬ".getBytes(StandardCharsets.UTF_8);//вынести

    private final DBServiceClient dbServiceClient;

    public ClientBasicAuthServiceImpl(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceClient.findByLogin(login)
                .map(client -> doesPasswordsMatch(password, client.getPassword()))
                .orElse(false);
    }

    private boolean doesPasswordsMatch(String passFromRequest, String passFromDb) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(SALT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hashedPasswordFromRequest = md.digest(passFromRequest.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(hashedPasswordFromRequest).equals(passFromDb);
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
