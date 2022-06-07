package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.model.Client;
import ru.otus.repository.DataTemplateHibernate;
import ru.otus.repository.HibernateUtils;
import ru.otus.server.ClientWebServer;
import ru.otus.server.ClientWebServerImpl;
import ru.otus.service.BasicAuthService;
import ru.otus.service.ClientBasicAuthServiceImpl;
import ru.otus.service.DbServiceClientImpl;
import ru.otus.service.TemplateProcessor;
import ru.otus.service.TemplateProcessorImpl;
import ru.otus.sessionmanager.TransactionManagerHibernate;

/*
    // Стартовая страница
    http://localhost:8080
 */
public class WebServerDemo {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        BasicAuthService authService = new ClientBasicAuthServiceImpl(dbServiceClient);

        ClientWebServer clientWebServer = new ClientWebServerImpl(authService, dbServiceClient, gson, templateProcessor, WEB_SERVER_PORT);

        clientWebServer.start();
        clientWebServer.join();
    }
}
