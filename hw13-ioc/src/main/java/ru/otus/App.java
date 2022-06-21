package ru.otus;

import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.config.AppConfig;
import ru.otus.config.AppConfig1;
import ru.otus.config.AppConfig2;
import ru.otus.services.GameProcessor;
import ru.otus.services.GameProcessorImpl;

/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.
Можно добавлять свои исключения.

PS Приложение представляет собой тренажер таблицы умножения
*/

public class App {

    public static void main(String[] args) throws Exception {
        /*
         * Вариант 1: 1 конфигурационный класс
         * для проверки закомментировать AppConfig1.class и AppConfig2.class
         */
        //AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);

        /*
         * Вариант 2: несколько конфигурационных классов
         * для проверки закомментировать AppConfig.class
         */
        //AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig1.class, AppConfig2.class);

        /*
         * Вариант 3: поиск конфигурационных классов по пакету
         * для проверки закомментировать AppConfig.class или AppConfig1.class и AppConfig2.class
         */
        //AppComponentsContainer container = new AppComponentsContainerImpl("ru.otus.config");


        //Приложение должно работать в каждом из указанных ниже вариантов
        //GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        //GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
        //GameProcessor gameProcessor = container.getAppComponent("gameProcessor");

        //gameProcessor.startGame();
    }
}
