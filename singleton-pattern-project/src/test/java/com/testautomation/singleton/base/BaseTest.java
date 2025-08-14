package com.testautomation.singleton.base;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.testautomation.singleton.driver.WebDriverSingleton;
import com.testautomation.singleton.utils.ConfigReader;

import lombok.extern.log4j.Log4j2;

/**
 * Base Test class for Singleton Pattern implementation
 * 
 * This class demonstrates:
 * - Singleton WebDriver usage in test execution
 * - Proper setup and teardown methods
 * - Thread-safe parallel execution support
 * - Configuration-driven test execution
 * 
 * @author Senior SDET
 */
@Log4j2
public class BaseTest {
    
    protected WebDriverSingleton webDriverSingleton;
    
    /**
     * Suite level setup - runs once before all tests
     */
    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        log.info("=== Test Suite Setup Started ===");
        
        // Print configuration for debugging
        ConfigReader.printAllProperties();
        
        // Initialize WebDriver Singleton
        webDriverSingleton = WebDriverSingleton.getInstance();
        
        log.info("WebDriver Singleton initialized successfully");
        log.info("=== Test Suite Setup Completed ===");
    }
    
    /**
     * Method level setup - runs before each test method
     */
    @BeforeMethod(alwaysRun = true)
    public void testSetup(Method method) {
        log.info("=== Test Setup Started: {} ===", method.getName());
        
        // Ensure WebDriver Singleton is initialized (thread-safe)
        webDriverSingleton = WebDriverSingleton.getInstance();
        
        // Get WebDriver for current thread
        WebDriver driver = webDriverSingleton.getDriver();
        
        log.info("WebDriver instance created for test: {} on thread: {}", 
                method.getName(), Thread.currentThread().getName());
        log.info("Browser: {}, Headless: {}, Remote: {}", 
                ConfigReader.getBrowser(), ConfigReader.isHeadless(), ConfigReader.isRemoteExecution());
        
        log.info("=== Test Setup Completed: {} ===", method.getName());
    }
    
    /**
     * Method level teardown - runs after each test method
     */
    @AfterMethod(alwaysRun = true)
    public void testTeardown(Method method) {
        log.info("=== Test Teardown Started: {} ===", method.getName());
        
        try {
            // Quit WebDriver for current thread
            if (webDriverSingleton != null) {
                webDriverSingleton.quitDriver();
                log.info("WebDriver quit successfully for test: {}", method.getName());
            } else {
                log.warn("WebDriver Singleton was null during teardown for: {}", method.getName());
            }
        } catch (Exception e) {
            log.error("Error during test teardown for: {}", method.getName(), e);
        }
        
        log.info("=== Test Teardown Completed: {} ===", method.getName());
    }
    
    /**
     * Suite level teardown - runs once after all tests
     */
    @AfterSuite(alwaysRun = true)
    public void suiteTeardown() {
        log.info("=== Test Suite Teardown Started ===");
        
        try {
            // Quit all remaining WebDriver instances
            if (webDriverSingleton != null) {
                webDriverSingleton.quitAllDrivers();
                log.info("All WebDriver instances quit successfully");
            }
        } catch (Exception e) {
            log.error("Error during suite teardown", e);
        }
        
        log.info("=== Test Suite Teardown Completed ===");
    }
    
    /**
     * Get WebDriver instance for current thread
     */
    protected WebDriver getDriver() {
        return webDriverSingleton.getDriver();
    }
    
    /**
     * Get WebDriver Singleton instance
     */
    protected WebDriverSingleton getWebDriverSingleton() {
        return webDriverSingleton;
    }
    
    /**
     * Navigate to URL
     */
    protected void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        getDriver().get(url);
    }
    
    /**
     * Get current page title
     */
    protected String getPageTitle() {
        String title = getDriver().getTitle();
        log.info("Current page title: {}", title);
        return title;
    }
    
    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        String url = getDriver().getCurrentUrl();
        log.info("Current URL: {}", url);
        return url;
    }
    
    /**
     * Wait for a specified duration (for demo purposes)
     * In real scenarios, use explicit waits instead
     */
    protected void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Wait interrupted", e);
        }
    }
}
