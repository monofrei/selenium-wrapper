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
        List<Class<?>> collect = Arrays.asList(parameters).stream()
                .map(o -> {
                    if (o instanceof Class) return ((Class<?>) o);
                    else return o.getClass();
                }).collect(Collectors.toList());
        Class<?>[] arguments = collect.toArray(new Class[collect.size()]);
        Constructor<?> constructor = type.getConstructor(arguments);

        //TODO temporary solution to handle when parent is null
        Object[] objects = Arrays.asList(parameters).stream()
                .map(o -> {
                    if (o instanceof Class && ((Class) o).getSimpleName().equals("Component")) return null;
                    else return o;
                }).collect(Collectors.toList()).toArray();

        return type.cast(constructor.newInstance(objects));
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

            createPageContent(browser, t);

            cache.put(t.getClass(), t);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Page createPageContent(Browser browser, Page page) throws IllegalAccessException {

        List<Field> elements = extractFieldsByPredicate(page.getClass(), isElement());

        for (Field field : elements) {
            field.setAccessible(true);

            Component component = createComponent(browser, field, null);
            field.set(page, component);
        }
        return page;
    }

    private Component createComponent(Browser browser, Field field, Component parent) {
        ElementAccessor annotation = field.getAnnotation(ElementAccessor.class);

        Class<? extends Component> elementType = (Class<? extends Component>) field.getType();

        Object parentObject = parent != null ? parent : Component.class;

        Component component = createInstance(elementType, field, browser, annotation.name(), parentObject, annotation.xpath());

        if (Arrays.asList(field.getType().getInterfaces()).contains(Module.class)) {
            //TODO init module
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
