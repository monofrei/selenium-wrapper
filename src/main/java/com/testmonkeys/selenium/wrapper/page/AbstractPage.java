package com.testmonkeys.selenium.wrapper.page;

import com.testmonkeys.selenium.wrapper.browser.Browser;

public abstract class AbstractPage implements Page {

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

    public String name() {
        return name;
    }

    public Browser getBrowser() {
        return browser;
    }

    @Override
    public void open() {
        browser.goTo(url);
    }

    @Override
    public String title() {
        return browser.getTitle();
    }

    @Override
    public boolean isCurrentPage() {
        return browser.getDriver().getCurrentUrl().startsWith(url);
    }
}
