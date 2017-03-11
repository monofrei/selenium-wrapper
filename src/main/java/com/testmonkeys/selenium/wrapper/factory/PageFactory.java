package com.testmonkeys.selenium.wrapper.factory;

import com.testmonkeys.selenium.wrapper.annotations.PageAccessor;
import com.testmonkeys.selenium.wrapper.browser.Browser;
import com.testmonkeys.selenium.wrapper.page.Page;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        return type.cast(constructor.newInstance(parameters));
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

            //TODO create content of the page

            cache.put(t.getClass(), t);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
