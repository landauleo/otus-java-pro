package homework;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {

    private int passed = 0;
    private int failed = 0;

    void run(String className) {
        Instant start = Instant.now();
        execute(className);
        Instant finish = Instant.now();
        analyse(start, finish);
    }

    private void execute(String className) {
        Class<?> clazz;
        try {
            clazz = getClassByName(className);
        } catch (ReflectiveOperationException e) {
            System.err.println("Unable to find Class with name " + className + ":");
            e.printStackTrace();
            return;
        }
        Object instance;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            System.err.println("Unable to instantiate object of " + className + ":");
            e.printStackTrace();
            return;
        }

        invoke(clazz, instance);
    }

    private void analyse(Instant start, Instant finish) {
        System.out.println("++++++++++++++ANALYTICS++++++++++++++++++++");
        System.out.println(passed + failed + " - launched");
        System.out.println(passed + " - passed");
        System.out.println(failed + " - failed");
        System.out.println("Started: " + start);
        System.out.println("Finished: " + finish);
        System.out.println("Duration: " + Duration.between(start, finish).getNano() + " nanoseconds");
        System.out.println("++++++++++++++++++THE+++END++++++++++++++++");

    }

    private Class<?> getClassByName(String className) throws ReflectiveOperationException {
        return Class.forName(className);
    }

    private List<Method> getAnnotatedBefore(Method[] methods) {
        return Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Before.class)).collect(Collectors.toList());
    }

    private List<Method> getAnnotatedTest(Method[] methods) {
        return Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Test.class)).collect(Collectors.toList());
    }

    private List<Method> getAnnotatedAfter(Method[] methods) {
        return Arrays.stream(methods).filter(method -> method.isAnnotationPresent(After.class)).collect(Collectors.toList());
    }

    private void invoke(Class<?> clazz, Object instance) {
        List<Method> annotatedBeforeMethods = getAnnotatedBefore(clazz.getDeclaredMethods()); //getMethods() and getDeclaredMethods() ARE NOT THE SAME
        List<Method> annotatedTestMethods = getAnnotatedTest(clazz.getDeclaredMethods());
        List<Method> annotatedAfterMethods = getAnnotatedAfter(clazz.getDeclaredMethods());

        annotatedTestMethods.forEach(testMethod -> {

            annotatedBeforeMethods.forEach(beforeMethod -> {
                try {
                    beforeMethod.invoke(instance);
                } catch (ReflectiveOperationException e) {
                    System.err.println("Unable to invoke methods annotated @Before for method " + testMethod + ":");
                    e.printStackTrace();
                }
            });

            try {
                testMethod.invoke(instance);
                System.out.println(testMethod.getName() + " PASSED");
                passed++;
            } catch (Exception e) {
                //Интересно: если сделать System.out, то строка выводится не в ожидаемом порядке, а в самом низу консоли
                System.out.println(testMethod.getName() + " FAILED");
                failed++;
            }

            annotatedAfterMethods.forEach(afterMethod -> {
                try {
                    afterMethod.invoke(instance);
                } catch (ReflectiveOperationException e) {
                    System.err.println("Unable to invoke methods annotated @After for method " + testMethod + ":");
                    e.printStackTrace();
                }
            });

        });
    }

}
