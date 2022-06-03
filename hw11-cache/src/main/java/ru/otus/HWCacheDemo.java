package ru.otus;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.CachedDbServiceClientImpl;
import ru.otus.crm.service.DbServiceClientImpl;


public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
    private static final String dbUrl = configuration.getProperty("hibernate.connection.url");
    private static final String dbUserName = configuration.getProperty("hibernate.connection.username");
    private static final String dbPassword = configuration.getProperty("hibernate.connection.password");
    private static final SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
    private static final TransactionManager transactionManager = new TransactionManagerHibernate(sessionFactory);
    private static final DataTemplate<Client> clientTemplate = new DataTemplateHibernate<>(Client.class);
    private static final DbServiceClientImpl dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
    private static LocalDateTime startDateTime;
    private static LocalDateTime endDateTime;

    public static void main(String[] args) {
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        tryBusinessLogicWithoutCache();
        tyrBusinessLogicWithCache();
    }


    private static void tryBusinessLogicWithoutCache() {
        var clientInitial = dbServiceClient.saveClient(new Client("clientName1"));

        logger.info("=========NOT CACHED=========");
        logger.info("Is about to get client:");
        startDateTime = LocalDateTime.now();
        dbServiceClient.getClient(clientInitial.getId());
        endDateTime = LocalDateTime.now();
        logger.info("=========> Got client for {} ns", Duration.between(startDateTime, endDateTime).toNanos()); //20668000

        dbServiceClient.saveClient(new Client(clientInitial.getId(), "clientName2"));
        logger.info("Is about to get updated client:");
        startDateTime = LocalDateTime.now();
        dbServiceClient.getClient(clientInitial.getId());
        endDateTime = LocalDateTime.now();
        logger.info("=========> Got updated client for {} ns", Duration.between(startDateTime, endDateTime).toNanos()); //3931000
    }

    private static void tyrBusinessLogicWithCache() {
        HwCache<String, Integer> cache = new MyCache<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        HwListener<String, Integer> listener = new HwListener<String, Integer>() {

            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);

        var cachedDbServiceClient = new CachedDbServiceClientImpl(transactionManager, clientTemplate);
        var clientInitial = cachedDbServiceClient.saveClient(new Client("cachedClientName1"));

        logger.info("=========CACHED=========");
        logger.info("Is about to get cachedClientName:");
        startDateTime = LocalDateTime.now();
        cachedDbServiceClient.getClient(clientInitial.getId());
        endDateTime = LocalDateTime.now();
        logger.info("=========> Got cachedClient for {} ns", Duration.between(startDateTime, endDateTime).toNanos()); //44000

        cachedDbServiceClient.saveClient(new Client(clientInitial.getId(), "clientName2"));
        logger.info("Is about to get updated cachedClient:");
        startDateTime = LocalDateTime.now();
        cachedDbServiceClient.getClient(clientInitial.getId());
        endDateTime = LocalDateTime.now();
        logger.info("=========> Got updated cachedClient for {} ns", Duration.between(startDateTime, endDateTime).toNanos()); //27000

        cache.removeListener(listener);
    }

}
