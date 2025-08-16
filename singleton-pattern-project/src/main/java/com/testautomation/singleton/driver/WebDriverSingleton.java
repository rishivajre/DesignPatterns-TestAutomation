package com.testautomation.singleton.driver;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;

/**
 * Thread-safe Singleton WebDriver Manager with comprehensive protections
 * 
 * Features:
 * - Thread-safe with double-checked locking
 * - Protection against cloning, serialization, and reflection attacks
 * - ThreadLocal WebDriver instances for parallel execution
 * - Support for local and remote execution
 * - Automatic browser setup with WebDriverManager
 * 
 * @author Sr SDET - Rishikesh Vajre
 */
@Log4j2
public class WebDriverSingleton implements Serializable, Cloneable {
    
    private static final long serialVersionUID = 1L; 
    // Added for serialization, what it does is to ensure that the same instance is deserialized
    private static volatile WebDriverSingleton instance;
    private static final Object lock = new Object();
    
    // ThreadLocal for parallel execution support
    private final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    private final ConcurrentMap<String, WebDriver> driverMap = new ConcurrentHashMap<>();
    
    // Configuration properties
    private final String browser;
    private final boolean headless;
    private final boolean remoteExecution;
    private final String gridUrl;
    private final Duration implicitWait;
    private final Duration pageLoadTimeout;
    
    /**
     * Private constructor with reflection attack protection
     */
    private WebDriverSingleton() {
        // Protection against reflection attacks
        if (instance != null) {
            throw new IllegalStateException("Instance already exists! Use getInstance() method.");
        }
        
        // Initialize configuration from properties
        this.browser = System.getProperty("browser", "chrome");
        this.headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        this.remoteExecution = Boolean.parseBoolean(System.getProperty("remote.execution", "false"));
        this.gridUrl = System.getProperty("grid.url", "http://localhost:4444");
        this.implicitWait = Duration.ofSeconds(Long.parseLong(System.getProperty("implicit.wait", "10")));
        this.pageLoadTimeout = Duration.ofSeconds(Long.parseLong(System.getProperty("page.load.timeout", "30")));
        
        log.info("WebDriverSingleton initialized with browser: {}, headless: {}, remote: {}", 
                browser, headless, remoteExecution);
    }
    
    /**
     * Thread-safe getInstance method with double-checked locking
     */
    public static WebDriverSingleton getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new WebDriverSingleton();
                }
            }
        }
        return instance;
    }
    
    /**
     * Get WebDriver instance for current thread
     */
    public WebDriver getDriver() {
        WebDriver driver = driverPool.get();
        if (driver == null) {
            driver = createWebDriver();
            configureWebDriver(driver);
            driverPool.set(driver);
            driverMap.put(Thread.currentThread().getName(), driver);
            log.info("Created new WebDriver instance for thread: {}", Thread.currentThread().getName());
        }
        return driver;
    }
    
    /**
     * Create WebDriver based on configuration
     */
    private WebDriver createWebDriver() {
        WebDriver driver;
        
        if (remoteExecution) {
            driver = createRemoteWebDriver();
        } else {
            driver = createLocalWebDriver();
        }
        
        return driver;
    }
    
    /**
     * Create local WebDriver
     */
    private WebDriver createLocalWebDriver() {
        WebDriver driver;
        
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
                
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        
        log.info("Created local {} WebDriver", browser);
        return driver;
    }
    
    /**
     * Create remote WebDriver for Selenium Grid
     */
    private WebDriver createRemoteWebDriver() {
        try {
            WebDriver driver;
            URI gridURI = URI.create(gridUrl);
            URL gridURL = gridURI.toURL();
            
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless");
                    }
                    chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");
                    driver = new RemoteWebDriver(gridURL, chromeOptions);
                    break;
                    
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    driver = new RemoteWebDriver(gridURL, firefoxOptions);
                    break;
                    
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) {
                        edgeOptions.addArguments("--headless");
                    }
                    driver = new RemoteWebDriver(gridURL, edgeOptions);
                    break;
                    
                default:
                    throw new IllegalArgumentException("Unsupported browser for remote execution: " + browser);
            }
            
            log.info("Created remote {} WebDriver with Grid URL: {}", browser, gridUrl);
            return driver;
            
        } catch (Exception e) {
            log.error("Failed to create remote WebDriver", e);
            throw new RuntimeException("Remote WebDriver creation failed", e);
        }
    }
    
    /**
     * Configure WebDriver with timeouts and settings
     */
    private void configureWebDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(implicitWait);
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout);
        driver.manage().window().maximize();
        
        log.info("Configured WebDriver with implicit wait: {} and page load timeout: {}", 
                implicitWait, pageLoadTimeout);
    }
    
    /**
     * Quit WebDriver for current thread
     */
    public void quitDriver() {
        WebDriver driver = driverPool.get();
        if (driver != null) {
            try {
                driver.quit();
                log.info("WebDriver quit successfully for thread: {}", Thread.currentThread().getName());
            } catch (Exception e) {
                log.error("Error quitting WebDriver", e);
            } finally {
                driverPool.remove();
                driverMap.remove(Thread.currentThread().getName());
            }
        }
    }
    
    /**
     * Quit all WebDriver instances
     */
    public void quitAllDrivers() {
        driverMap.values().forEach(driver -> {
            try {
                if (driver != null) {
                    driver.quit();
                }
            } catch (Exception e) {
                log.error("Error quitting WebDriver", e);
            }
        });
        driverMap.clear();
        log.info("All WebDriver instances quit successfully");
    }
    
    /**
     * Get active driver count
     */
    public int getActiveDriverCount() {
        return driverMap.size();
    }
    
    /**
     * Protection against cloning
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton instance cannot be cloned");
    }
    
    /**
     * Protection against serialization attacks
     */
    protected Object readResolve() {
        return getInstance();
    }
}

/**
 * Alternative Enum-based Singleton implementation (more secure)
 * 
 * This approach is inherently thread-safe and provides better protection
 * against serialization and reflection attacks.
 */
enum WebDriverEnum {
    INSTANCE;
    
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    public WebDriver getDriver() {
        // Implementation similar to above
        return driverThreadLocal.get();
    }
    
    public void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }
    
    public void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
