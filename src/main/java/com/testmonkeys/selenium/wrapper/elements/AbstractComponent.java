package com.testmonkeys.selenium.wrapper.elements;

import com.testmonkeys.selenium.wrapper.browser.Browser;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AbstractComponent implements Component {

    private String name;
    private String xpath;
    private Browser browser;

    private Component parent;

    public AbstractComponent(Browser browser, String name, Component parent, String xpath) {
        this.name = name;
        this.xpath = xpath;
        this.browser = browser;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getXpath() {
        return xpath;
    }

    @Override
    public Component getParent() {
        return parent;
    }

    public WebElement find() {
        return browser.findElement(this.getFullXpath());
    }

    @Override
    public Browser getBrowser() {
        return browser;
    }

    @Override
    public List<WebElement> findAll() {
        return browser.findElements(this.getFullXpath());
    }

    @Override
    public String getFullXpath() {
        return parent != null ? parent.getFullXpath() + xpath : xpath;
    }
}
