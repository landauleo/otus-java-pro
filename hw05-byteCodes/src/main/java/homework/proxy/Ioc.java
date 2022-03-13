package homework.proxy;

import homework.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Ioc {

    private static List<String> methodsToBeProxied = new ArrayList<>();

    private Ioc() {
    }

    static TestLogging createProxiedTestLogging() {
        Arrays.stream(TestLoggingImpl.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Log.class))
                .forEach(method -> methodsToBeProxied.add(method.getName()+ Arrays.toString(method.getParameters()))); //очень похоже на костыль, но по хэшкоду, самому уникальному из того, чем обладает метод, сравнивать по понятным причинам не получается

        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
        }

        @Override
        //в аргументе method приходит clazz типа TestLogging (интерфейс!!!), поэтому в этом месте нельзя смотреть, есть ли аннотации
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //на этом моменте возник вопрос: почему названия методов и параметры у простого и проксированного метода совпадают, а модификаторы нет (1 и 125)
            if (methodsToBeProxied.contains(method.getName() + Arrays.toString(method.getParameters()))) {
                System.out.println("------------٩(◕‿◕)۶------------");
                System.out.println("invoking method:" + method.getName() + " with arguments " + Arrays.toString(args));
                System.out.println("-----------･ﾟ･(｡>ω<｡)･ﾟ---------");
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
