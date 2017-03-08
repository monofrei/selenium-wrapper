package com.testmonkeys.selenium.wrapper.page;

import com.testmonkeys.selenium.wrapper.browser.Browser;

public abstract class AbstractPage {

    private String url;
    private String name;
    private Browser browser;

    public AbstractPage(String url, String name, Browser browser) {
        this.url = url;
        this.name = name;
        this.browser = browser;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Browser getBrowser() {
        return browser;
    }
}
