package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exception.AppComponentsContainerException;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            processConfig(initialConfigClass);
        } catch (Exception e) {
            System.err.println("APPLICATION FAILED TO START:");
        }
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Object configClassInstance = getNewInstanceByClass(configClass);
        List<Method> components = getComponents(configClass);
        for (Method method : components) { //интересно, что при foreach исключение, которое сначала было в сигнатуре стало серым и idea предлагала завернуть всё в try/catch
            invoke(method, configClassInstance);
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
        return (C) appComponents.stream()
                .filter(appComponent -> componentClass.isAssignableFrom(appComponent.getClass())) //if the class or interface represented by this Class object is either the same as, or is a superclass or superinterface of
                .findFirst().orElseThrow(() -> new AppComponentsContainerException("Can't get component of class " + componentClass.getSimpleName()));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) Optional.ofNullable(appComponentsByName.get(componentName))
                .orElseThrow(() -> new AppComponentsContainerException("Can't get component " + componentName));
    }

    private void invoke(Method method, Object configClassInstance) {
        String componentName = method.getName();
        ;
        try {
            if (appComponentsByName.containsKey(componentName)) {
                throw new AppComponentsContainerException("Duplicate components " + componentName + " in config class " + configClassInstance.getClass().getSimpleName());
            }
            Object[] params = Arrays.stream(method.getParameterTypes()).map(this::getAppComponent).toArray();
            Object newComponent = method.invoke(configClassInstance, params);
            appComponents.add(newComponent);
            appComponentsByName.put(componentName, newComponent);
        } catch (Exception e) {
            throw new AppComponentsContainerException("Failed to invoke " + componentName, e);
        }

    }

    private Object getNewInstanceByClass(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new AppComponentsContainerException("Failed to create " + clazz.getSimpleName(), e);
        }
    }

}
