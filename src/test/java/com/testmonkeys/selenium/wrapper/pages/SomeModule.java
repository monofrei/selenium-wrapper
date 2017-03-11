package com.testmonkeys.selenium.wrapper.pages;

import com.testmonkeys.selenium.wrapper.annotations.ElementAccessor;
import com.testmonkeys.selenium.wrapper.browser.Browser;
import com.testmonkeys.selenium.wrapper.elements.AbstractComponent;
import com.testmonkeys.selenium.wrapper.elements.Component;
import com.testmonkeys.selenium.wrapper.elements.Module;
import com.testmonkeys.selenium.wrapper.elements.impl.Button;

public class SomeModule extends AbstractComponent implements Module {

    @ElementAccessor(name = "Module Button", xpath = "//button")
    private Button moduleButton;

    public SomeModule(Browser browser, String name, Component parent, String xpath) {
        super(browser, name, parent, xpath);
    }

    public Button moduleButton() {
        return moduleButton;
    }
}
