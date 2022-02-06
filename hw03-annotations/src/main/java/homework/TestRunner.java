package homework;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {

    private final static Class<? extends Annotation> BEFORE_CLASS = Before.class;
    private final static Class<? extends Annotation> TEST_CLASS = Test.class;
    private final static Class<? extends Annotation> AFTER_CLASS = After.class;

    private static int passed = 0;
    private static int failed = 0;

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
        List<Method> annotatedBeforeMethods = filterByAnnotationClass(clazz.getDeclaredMethods(), BEFORE_CLASS); //getMethods() and getDeclaredMethods() ARE NOT THE SAME
        List<Method> annotatedTestMethods = filterByAnnotationClass(clazz.getDeclaredMethods(), TEST_CLASS);
        List<Method> annotatedAfterMethods = filterByAnnotationClass(clazz.getDeclaredMethods(), AFTER_CLASS);

        annotatedTestMethods.forEach(method -> invoke(method, clazz, annotatedBeforeMethods, annotatedAfterMethods));
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

    private Object getNewInstanceByClass(Class<?> clazz) throws ReflectiveOperationException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    private List<Method> filterByAnnotationClass(Method[] methods, Class<? extends Annotation> clazz) {
        return Arrays.stream(methods).filter(method -> method.isAnnotationPresent(clazz)).collect(Collectors.toList());
    }

    private void invoke(Method testMethod, Class<?> clazz, List<Method> annotatedBeforeMethods, List<Method> annotatedAfterMethods) {
        Object instance;
        try {
            instance = getNewInstanceByClass(clazz);
        } catch (ReflectiveOperationException e) {
            System.err.println("Unable to instantiate object of class " + clazz.getName() + " and run test " + testMethod.getName());
            e.printStackTrace();
            return;
        }
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
            //Интересно: если сделать System.err, то строка выводится не в ожидаемом порядке, а в самом низу консоли в отличие от System.out
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
    }
}
