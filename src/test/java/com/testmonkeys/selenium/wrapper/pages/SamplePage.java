package com.testmonkeys.selenium.wrapper.pages;

import com.testmonkeys.selenium.wrapper.annotations.ElementAccessor;
import com.testmonkeys.selenium.wrapper.annotations.PageAccessor;
import com.testmonkeys.selenium.wrapper.browser.Browser;
import com.testmonkeys.selenium.wrapper.elements.impl.Button;
import com.testmonkeys.selenium.wrapper.page.AbstractPage;

@PageAccessor(name = "Sample page", url = "/samplePage")
public class SamplePage extends AbstractPage {

    @ElementAccessor(name = "Button", xpath = "//*")
    private Button button;

    @ElementAccessor(name = "Sample Module", xpath = "//module")
    private SomeModule someModule;

    public SamplePage(Browser browser, String url, String name) {
        super(url, name, browser);
    }

    public Button button() {
        return button;
    }

    public SomeModule someModule() {
        return someModule;
    }
}
