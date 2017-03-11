package com.testmonkeys.selenium.wrapper.elements.impl;

import com.testmonkeys.selenium.wrapper.browser.Browser;
import com.testmonkeys.selenium.wrapper.elements.AbstractComponent;
import com.testmonkeys.selenium.wrapper.elements.Component;

public class Button extends AbstractComponent {

    public Button(Browser browser, String name, Component parent, String xpath) {
        super(name, xpath, browser, parent);
    }

    public void click() {
        this.find().click();
    }
}
