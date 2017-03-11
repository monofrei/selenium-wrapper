package com.testmonkeys.selenium.wrapper;

import com.testmonkeys.selenium.wrapper.browser.Browser;
import com.testmonkeys.selenium.wrapper.factory.PageFactory;
import com.testmonkeys.selenium.wrapper.page.Page;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-context.xml"})
public class PageAccessorFactoryTest {

    @Autowired
    private PageFactory pageFactory;

    @Autowired
    private Browser browser;

    @After
    public void tearDown() {
        browser.quit();
    }

    @Test
    public void createPageTest() throws ClassNotFoundException {
        Page page = pageFactory.createPage("Sample page");
        assertThat(page, notNullValue());
        assertThat(page.getUrl(), is("http://google.com/samplePage"));
    }

}
