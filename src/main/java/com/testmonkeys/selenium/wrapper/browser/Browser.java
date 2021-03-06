package com.testmonkeys.selenium.wrapper.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Browser {

    private WebDriver driver;

    private FluentWait<WebDriver> waiter;

    private int timeout;

    private int step;

    private TimeUnit unit;

    public Browser(WebDriver driver) {
        this.driver = driver;
        //TODO maximize based on external parameter
        this.driver.manage().window().maximize();
        waiter = initWaitter(10, 1, TimeUnit.SECONDS);
    }

    public Browser(WebDriver driver, int timeout, int step, TimeUnit unit) {
        this.driver = driver;
        //TODO maximize based on external parameter
        this.driver.manage().window().maximize();
        waiter = initWaitter(timeout, step, unit);
    }


    public WebElement findElement(String xpath) {
        return waiter.until(webDriver -> webDriver.findElement(By.xpath(xpath)));
    }

    public List<WebElement> findElements(String xpath) {
        return waiter.until(webDriver -> webDriver.findElements(By.xpath(xpath)));
    }

    private FluentWait<WebDriver> initWaitter(int timeout, int step, TimeUnit unit) {
        return new FluentWait<>(this.driver)
                .withTimeout(timeout, unit)
                .pollingEvery(step, unit)
                .ignoring(NoSuchElementException.class);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void goTo(String url) {
        driver.navigate().to(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void quit() {
        this.driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
