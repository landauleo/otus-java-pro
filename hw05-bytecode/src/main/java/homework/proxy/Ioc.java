package homework.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import homework.annotation.Log;

class Ioc {

    private Ioc() {
    }

    static TestLogging createProxiedTestLogging(TestLogging testLogging) {
        InvocationHandler handler = new DemoInvocationHandler(testLogging);
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;
        private final Set<String> methodsToBeProxied = new HashSet<>();

        DemoInvocationHandler(TestLogging testLogging) {
            Arrays.stream(testLogging.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Log.class))
                    .forEach(method -> methodsToBeProxied.add(method.getName() + Arrays.toString(method.getParameters()))); //очень похоже на костыль, но по хэшкоду, самому уникальному из того, чем обладает метод, сравнивать по понятным причинам не получается

            this.testLogging = testLogging;
        }

        @Override
        //в аргументе method приходит clazz типа TestLogging (интерфейс!!!), поэтому в этом месте нельзя смотреть, есть ли аннотации
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //на этом моменте возник вопрос: почему названия методов и параметры у простого и проксированного метода совпадают, а модификаторы нет (1 и 125)
            //update: проксированный метод приобретает modifiers: public (код 1) abstract (код 1024) -> 1025 код модификаторов
            if (methodsToBeProxied.contains(method.getName() + Arrays.toString(method.getParameters()))) {
                System.out.println("------------٩(◕‿◕)۶------------");
                System.out.println("invoking method:" + method.getName() + " with arguments " + Arrays.toString(args));
                System.out.println("-----------･ﾟ･(｡>ω<｡)･ﾟ---------");
            }
            return method.invoke(testLogging, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "testLogging=" + testLogging +
                    '}';
        }
    }
}
