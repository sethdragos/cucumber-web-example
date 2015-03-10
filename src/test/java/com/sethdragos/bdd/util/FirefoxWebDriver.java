package com.sethdragos.bdd.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

import java.util.logging.Logger;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;


/**
 * Inspired by <a href="https://www.coveros.com/819">Cucumber-Webdriver Best Practices</a> tutorial.
 */
public class FirefoxWebDriver {

    private static final Logger LOGGER = Logger.getLogger(FirefoxWebDriver.class.getName());
    private static WebDriver webDriver;

    public enum Locators {xpath, css, id, name, classname, paritallinktext, linktext, tagname}

    public synchronized static WebDriver getDriver() {
        if (webDriver == null) {
            try {
                webDriver = new FirefoxDriver(new FirefoxProfile());
                LOGGER.info("starting Firefox");
            } finally {
                Runtime.getRuntime().addShutdownHook(
                        new Thread(new BrowserCleanup()));
            }
        }
        return webDriver;
    }

    //a method for waiting until an element is displayed
    public static void waitForElementDisplayed(Locators locator, String element) throws Exception {
        waitForElementDisplayed(getWebElement(locator, element), 5);
    }

    public static void waitForElementDisplayed(Locators locator, String element, int seconds) throws Exception {
        waitForElementDisplayed(getWebElement(locator, element), seconds);
    }

    public static void waitForElementDisplayed(WebElement element) throws Exception {
        waitForElementDisplayed(element, 5);
    }

    public static void waitForElementDisplayed(WebElement element, int seconds) throws Exception {
        //wait for up to XX seconds for our error message
        long end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            // If results have been returned, the results are displayed in a drop down.
            if (element.isDisplayed()) {
                break;
            }
        }
    }

    //a method for checking id an element is displayed
    public static void checkElementDisplayed(Locators locator, String element) throws Exception {
        checkElementDisplayed(getWebElement(locator, element));
    }

    public static void checkElementDisplayed(WebElement element) throws Exception {
        assertTrue("Element is NOT displayed", element.isDisplayed());
    }

    //a generic selenium click functionality implemented
    public static void click(Locators locator, String element) throws Exception {
        click(getWebElement(locator, element));
    }

    public static void click(WebElement element) {
        Actions selAction = new Actions(webDriver);
        selAction.click(element).perform();
    }

    public static void close() {
        try {
            getDriver().quit();
            webDriver = null;
            LOGGER.info("closing the browser");
        } catch (Exception e) {
        }
    }

    //a generic selenium 'input text' functionality
    public static void inputText(Locators locator, String element, String text) throws Exception {
        inputText(getWebElement(locator, element), text);
    }

    public static void inputText(WebElement element, String text) {
        element.clear();
        Actions selAction = new Actions(webDriver);
        selAction.sendKeys(element, text).perform();
    }

    //a method to grab the web element using selenium webdriver
    public static WebElement getWebElement(Locators locator, String path) throws Exception {
        By byElement;
        switch (locator) {        //determine which locator item we are interested in
            case xpath: {
                byElement = By.xpath(path);
                break;
            }
            case id: {
                byElement = By.id(path);
                break;
            }
            case css: {
                byElement = By.cssSelector(path);
                break;
            }
            case name: {
                byElement = By.name(path);
                break;
            }
            case classname: {
                byElement = By.className(path);
                break;
            }
            case linktext: {
                byElement = By.linkText(path);
                break;
            }
            case paritallinktext: {
                byElement = By.partialLinkText(path);
                break;
            }
            case tagname: {
                byElement = By.tagName(path);
                break;
            }
            default: {
                throw new Exception();
            }
        }
        return webDriver.findElement(byElement);
    }

    private static class BrowserCleanup implements Runnable {
        public void run() {
            close();
        }
    }
}
