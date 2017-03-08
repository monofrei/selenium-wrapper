package com.testmonkeys.selenium.wrapper.elements;

import com.testmonkeys.selenium.wrapper.browser.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AbstractWebElement {

    private String name;
    private By locator;
    private Browser browser;

    private AbstractWebElement parent;

    public AbstractWebElement(String name, By locator, Browser browser, AbstractWebElement parent) {
        this.name = name;
        this.locator = locator;
        this.browser = browser;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public By getLocator() {
        return locator;
    }

    protected WebElement find() {
        return browser.findElement(this.locator);
    }
}
