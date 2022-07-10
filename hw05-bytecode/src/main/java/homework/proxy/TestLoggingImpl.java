package homework.proxy;

import homework.annotation.Log;

public class TestLoggingImpl implements TestLogging {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("I'm the first one with param " + param);
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println("I'm the second one with params " + param1 + " " + param2);
    }

    @Override
    public void calculation(int param1, int param2, int param3) {
        System.out.println("I DON'T WANNA BE LOGGED");
    }
}
