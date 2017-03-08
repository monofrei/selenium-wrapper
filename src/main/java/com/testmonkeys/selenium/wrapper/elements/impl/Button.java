package com.testmonkeys.selenium.wrapper.elements.impl;

import com.testmonkeys.selenium.wrapper.browser.Browser;
import com.testmonkeys.selenium.wrapper.elements.AbstractWebElement;
import org.openqa.selenium.By;

public class Button extends AbstractWebElement {

    public Button(String name, By locator, Browser browser, AbstractWebElement parent) {
        super(name, locator, browser, parent);
    }

    public void click() {
        this.find().click();
    }
}
