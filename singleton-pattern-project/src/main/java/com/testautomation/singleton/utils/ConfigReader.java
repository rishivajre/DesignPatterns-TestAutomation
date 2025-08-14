package com.testautomation.singleton.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading configuration properties
 * 
 * This class provides centralized configuration management for the test automation framework
 * Supports different environments and fallback to default values
 * 
 * @author Senior SDET
 */
@Log4j2
public class ConfigReader {
    
    private static final String CONFIG_FILE = "config.properties";
    private static final String TEST_DATA_CONFIG = "testdata.properties";
    private static Properties properties;
    private static Properties testDataProperties;
    
    static {
        loadProperties();
        loadTestDataProperties();
    }
    
    /**
     * Load main configuration properties
     */
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = ConfigReader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            
            if (inputStream != null) {
                properties.load(inputStream);
                log.info("Configuration properties loaded successfully from {}", CONFIG_FILE);
            } else {
                log.warn("Configuration file {} not found, using default values", CONFIG_FILE);
            }
        } catch (IOException e) {
            log.error("Error loading configuration properties", e);
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }
    
    /**
     * Load test data properties
     */
    private static void loadTestDataProperties() {
        testDataProperties = new Properties();
        try (InputStream inputStream = ConfigReader.class.getClassLoader()
                .getResourceAsStream(TEST_DATA_CONFIG)) {
            
            if (inputStream != null) {
                testDataProperties.load(inputStream);
                log.info("Test data properties loaded successfully from {}", TEST_DATA_CONFIG);
            } else {
                log.warn("Test data file {} not found", TEST_DATA_CONFIG);
            }
        } catch (IOException e) {
            log.error("Error loading test data properties", e);
        }
    }
    
    /**
     * Get property value with system property override
     */
    public static String getProperty(String key) {
        // First check system properties (for runtime overrides)
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isEmpty()) {
            return systemValue;
        }
        
        // Then check config file
        return properties.getProperty(key);
    }
    
    /**
     * Get property with default value
     */
    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }
    
    /**
     * Get boolean property
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }
    
    /**
     * Get integer property
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                log.warn("Invalid integer value for key {}: {}, using default: {}", key, value, defaultValue);
            }
        }
        return defaultValue;
    }
    
    /**
     * Get test data property
     */
    public static String getTestDataProperty(String key) {
        return testDataProperties.getProperty(key);
    }
    
    /**
     * Get test data property with default value
     */
    public static String getTestDataProperty(String key, String defaultValue) {
        String value = getTestDataProperty(key);
        return value != null ? value : defaultValue;
    }
    
    // Browser Configuration
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    public static boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }
    
    public static boolean isRemoteExecution() {
        return getBooleanProperty("remote.execution", false);
    }
    
    public static String getGridUrl() {
        return getProperty("grid.url", "http://localhost:4444");
    }
    
    // Timeout Configuration
    public static int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }
    
    public static int getExplicitWait() {
        return getIntProperty("explicit.wait", 20);
    }
    
    public static int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }
    
    // Application URLs
    public static String getSpiceJetUrl() {
        return getProperty("spicejet.url", "https://www.spicejet.com/");
    }
    
    public static String getOrangeHrmUrl() {
        return getProperty("orangehrm.url", "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }
    
    public static String getAutomationExerciseUrl() {
        return getProperty("automation.exercise.url", "https://www.automationexercise.com/");
    }
    
    // Test Data Configuration
    public static String getFromCity() {
        return getTestDataProperty("spicejet.from.city", "Delhi");
    }
    
    public static String getToCity() {
        return getTestDataProperty("spicejet.to.city", "Mumbai");
    }
    
    public static String getTripType() {
        return getTestDataProperty("spicejet.trip.type", "One Way");
    }
    
    // LambdaTest Configuration
    public static String getLambdaTestUsername() {
        return getProperty("lambdatest.username");
    }
    
    public static String getLambdaTestAccessKey() {
        return getProperty("lambdatest.accesskey");
    }
    
    public static String getLambdaTestGridUrl() {
        return getProperty("lambdatest.grid.url", "https://hub.lambdatest.com/wd/hub");
    }
    
    // BrowserStack Configuration
    public static String getBrowserStackUsername() {
        return getProperty("browserstack.username");
    }
    
    public static String getBrowserStackAccessKey() {
        return getProperty("browserstack.accesskey");
    }
    
    public static String getBrowserStackUrl() {
        return getProperty("browserstack.url", "https://hub-cloud.browserstack.com/wd/hub");
    }
    
    // Reporting Configuration
    public static String getExtentReportPath() {
        return getProperty("extent.report.path", "target/extent-reports/");
    }
    
    public static String getAllureResultsPath() {
        return getProperty("allure.results.path", "target/allure-results/");
    }
    
    public static String getScreenshotPath() {
        return getProperty("screenshot.path", "target/screenshots/");
    }
    
    // Environment Configuration
    public static String getEnvironment() {
        return getProperty("environment", "local");
    }
    
    /**
     * Print all loaded properties for debugging
     */
    public static void printAllProperties() {
        log.info("=== Configuration Properties ===");
        properties.forEach((key, value) -> log.info("{} = {}", key, value));
        
        log.info("=== Test Data Properties ===");
        testDataProperties.forEach((key, value) -> log.info("{} = {}", key, value));
        
        log.info("=== System Properties (overrides) ===");
        System.getProperties().entrySet().stream()
            .filter(entry -> properties.containsKey(entry.getKey().toString()))
            .forEach(entry -> log.info("{} = {} (system override)", entry.getKey(), entry.getValue()));
    }
}
