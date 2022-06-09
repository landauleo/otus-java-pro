package ru.otus.servlet;

import java.io.IOException;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.model.Client;
import ru.otus.service.DBServiceClient;

import static ru.otus.helpers.HashHelper.getHashedPass;

public class ClientApiServlet extends HttpServlet {

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

        String hashedPassword = getHashedPass(request.getParameter("password"));

        client.setPassword(hashedPassword);
        return client;
    }


}
