package com.testmonkeys.selenium.wrapper.page;

public interface Page {

    void open();

    String getUrl();

    String name();

    String title();

    boolean isCurrentPage();
}
