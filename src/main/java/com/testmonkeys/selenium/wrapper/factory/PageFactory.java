package com.testmonkeys.selenium.wrapper.factory;

import com.testmonkeys.selenium.wrapper.annotations.ElementAccessor;
import com.testmonkeys.selenium.wrapper.annotations.PageAccessor;
import com.testmonkeys.selenium.wrapper.browser.Browser;
import com.testmonkeys.selenium.wrapper.elements.AbstractComponent;
import com.testmonkeys.selenium.wrapper.elements.Component;
import com.testmonkeys.selenium.wrapper.elements.Module;
import com.testmonkeys.selenium.wrapper.page.Page;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.testmonkeys.selenium.wrapper.utils.ReflectionUtils.extractFieldsByPredicate;

public class PageFactory {

    private Browser browser;

    private PageScanner scanner;

    private Map<Class<? extends Page>, Page> cache;

    private String baseUrl;

    public PageFactory(Browser browser, PageScanner scanner, String baseUrl) {
        this.browser = browser;
        this.scanner = scanner;
        this.baseUrl = baseUrl;
        this.cache = new HashMap<>();
    }

    public static <T> T newInstance(Class<T> type, Object... parameters) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        //TODO the commented logic identifies automatically the constructor but because the parent component is of type Interface but in this method it comes as the
        //TODO concrete class it failes, need to find a better solution, for now get first constructor is fine.
//        List<Class<?>> collect = new ArrayList<>();
//
//        for (Object parameter : parameters) {
//            if (parameter instanceof Class) collect.add((Class<?>) parameter);
//            else collect.add(parameter.getClass());
//        }
//
//        Class<?>[] arguments = collect.toArray(new Class[collect.size()]);
//        Constructor<?> constructor = type.getConstructor(arguments);

        Constructor<?> constructor = type.getDeclaredConstructors()[0];
        parameters = Arrays.stream(parameters).map(p -> {
            if (p instanceof Class) return null;
            else return p;
        }).collect(Collectors.toList()).toArray();

        return type.cast(constructor.newInstance(parameters));
    }

    private static Predicate<Field> isElement() {
        return field -> AbstractComponent.class.isAssignableFrom(field.getType());
    }

    public Page createPage(String name) throws ClassNotFoundException {
        Class<? extends Page> page = scanner.getPageByName(name);
        return createPage(page);
    }

    private <T extends Page> T createPage(Class<T> type) {
        try {
            Page page = cache.get(type);
            if (page != null) return type.cast(page);

            PageAccessor[] pageAccessors = type.getAnnotationsByType(PageAccessor.class);
            if (pageAccessors.length != 1)
                throw new RuntimeException("Page of type [" + type + "] has wrong number of PageAccessor annotations.");

            PageAccessor pageAccessor = pageAccessors[0];
            String url = baseUrl + pageAccessor.url();

            T t = newInstance(type, this.browser, url, pageAccessor.name());

            createPageContent(browser, t, null);

            cache.put(t.getClass(), t);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T createPageContent(Browser browser, T container, Component parent) throws IllegalAccessException {

        List<Field> elements = extractFieldsByPredicate(container.getClass(), isElement());

        for (Field field : elements) {
            field.setAccessible(true);

            Component component = createComponent(browser, field, parent);
            field.set(container, component);
        }
        return container;
    }

    private Component createComponent(Browser browser, Field field, Component parent) throws IllegalAccessException {
        ElementAccessor annotation = field.getAnnotation(ElementAccessor.class);

        Class<? extends Component> elementType = (Class<? extends Component>) field.getType();

        Object parentObject = parent != null ? parent : Component.class;

        Component component = createInstance(elementType, field, browser, annotation.name(), parentObject, annotation.xpath());

        if (Arrays.asList(field.getType().getInterfaces()).contains(Module.class)) {
            createPageContent(browser, component, component);
        }

        return component;
    }

    private <T extends Component> T createInstance(Class<T> type, Field field, Object... parameters) {
        Object[] array = parameters;

        //TODO handle GroupComponent

        try {
            return newInstance(type, array);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
