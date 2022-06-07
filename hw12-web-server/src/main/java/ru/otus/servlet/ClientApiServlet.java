package ru.otus.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.model.Client;
import ru.otus.service.DBServiceClient;

public class ClientApiServlet extends HttpServlet {

    private static final byte[] SALT = "ВОТ ЭТО СОЛЬ".getBytes(StandardCharsets.UTF_8);//вынести
    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = dbServiceClient.saveClient(extractClientsParametersFromRequest(request));
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(client));
    }

    private Client extractClientsParametersFromRequest(HttpServletRequest request) {
        Client client = new Client();
        client.setName(request.getParameter("name"));
        client.setLogin(request.getParameter("login"));
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(SALT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hashedPassword = md.digest(request.getParameter("password").getBytes(StandardCharsets.UTF_8));

        client.setPassword(bytesToHex(hashedPassword));
        return client;
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
