package com.testmonkeys.selenium.wrapper.factory;

import com.testmonkeys.selenium.wrapper.browser.Browser;
import com.testmonkeys.selenium.wrapper.page.Page;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class PageFactory {

    private Browser browser;

    private String pagesPackage;

    @Autowired
    public PageFactory(Browser browser, String pagesPackage) {
        this.browser = browser;
        this.pagesPackage = pagesPackage;
    }

    public Page createPage(String name) throws ClassNotFoundException {
        Class<? extends Page> page = getPage(name);
        return null;
    }

    private Class<? extends Page> getPage(String pageName) throws ClassNotFoundException {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(pagesPackage))
                .setScanners(new SubTypesScanner()));

        return reflections.getSubTypesOf(Page.class)
                .stream().filter(p -> p.getName().equals(pageName))
                .findFirst().orElseThrow(() -> new ClassNotFoundException("Page [" + pageName + "] could not be found"));
    }
}
