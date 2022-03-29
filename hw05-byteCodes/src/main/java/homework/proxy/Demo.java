package homework.proxy;

public class Demo {

    public void action() {
        TestLogging testLogging = Ioc.createProxiedTestLogging(new TestLoggingImpl());

        testLogging.calculation(6);
        testLogging.calculation(6, 66);
        testLogging.calculation(6, 66, 666);
    }
}
