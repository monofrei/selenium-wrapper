package com.testmonkeys.selenium.wrapper.elements;

import com.testmonkeys.selenium.wrapper.browser.Browser;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface Component {

    String getXpath();

    Component getParent();

    WebElement find();

    Browser getBrowser();

    List<WebElement> findAll();

    String getFullXpath();

}
