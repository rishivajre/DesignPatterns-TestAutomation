package main.java.com.testautomation.factory.driver;

import org.openqa.selenium.WebDriver;

/**
 * Abstract Factory interface for WebDriver creation
 * 
 * This interface defines the contract for creating WebDriver instances
 * both locally and remotely, enabling easy switching between different
 * browsers and execution environments
 * 
 * @author Senior SDET
 */
public interface WebDriverFactory {
    
    /**
     * Create a local WebDriver instance
     * @return WebDriver instance configured for local execution
     */
    WebDriver createDriver();
    
    /**
     * Create a remote WebDriver instance for Selenium Grid
     * @param gridUrl The Selenium Grid hub URL
     * @return RemoteWebDriver instance configured for grid execution
     */
    WebDriver createRemoteDriver(String gridUrl);
    
    /**
     * Get the browser type supported by this factory
     * @return String representation of the browser type
     */
    String getBrowserType();
    
    /**
     * Check if this factory supports headless execution
     * @return true if headless mode is supported, false otherwise
     */
    boolean supportsHeadless();
}
