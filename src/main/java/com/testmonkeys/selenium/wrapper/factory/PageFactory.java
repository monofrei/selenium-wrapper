package com.testmonkeys.selenium.wrapper.factory;

import com.testmonkeys.selenium.wrapper.browser.Browser;
import com.testmonkeys.selenium.wrapper.page.Page;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
//@DependsOn("browser")
public class PageFactory {

    private Browser browser;

    //@Value("${pagesPackage}")
    private String pagesPackage;

    @Autowired
    public PageFactory(Browser browser, String pagesPackage) {
        this.browser = browser;
        this.pagesPackage = pagesPackage;
    }

    public Page createPage(String name) {
        return null;
    }
}
