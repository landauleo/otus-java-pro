package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            processConfig(initialConfigClass);
        } catch (ReflectiveOperationException e) {
            System.err.println("APPLICATION FAILED TO START:");
            throw new RuntimeException(e);
        }
    }

    private void processConfig(Class<?> configClass) throws ReflectiveOperationException {
        checkConfigClass(configClass);
        List<Method> components = getComponents(configClass);
        for (Method method : components) { //интересно, что при foreach исключение в сигнатуре было серым и idea предлагала завернуть всё в try/catch
            invoke(method, method.getClass());
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private List<Method> getComponents(Class<?> configClass) {
        List<Method> components = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(component -> component.getDeclaredAnnotation(AppComponent.class).order()))
                .collect(Collectors.toList());
        if (components.isEmpty()) {
            throw new IllegalArgumentException(String.format("No components in given config class %s", configClass.getName()));
        }
        return components;
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream().filter(component -> component.getClass().equals(componentClass)).findFirst().orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("No components by name %s", componentClass.getName()));
        });
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private void invoke(Method method, Class<?> clazz) throws ReflectiveOperationException {
        Object instance = getNewInstanceByClass(clazz);
        method.invoke(instance);
        appComponents.add(instance);
        appComponentsByName.put(clazz.getName(), instance);
        System.out.println(method.getName() + " INSTANTIATED");

    }

    private Object getNewInstanceByClass(Class<?> clazz) throws ReflectiveOperationException {
        return clazz.getDeclaredConstructor().newInstance();
    }

}
