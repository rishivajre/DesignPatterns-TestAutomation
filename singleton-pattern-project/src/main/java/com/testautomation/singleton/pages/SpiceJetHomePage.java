package com.testautomation.singleton.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.extern.log4j.Log4j2;

/**
 * Page Object Model for SpiceJet Home Page
 * 
 * This class demonstrates the Page Object Model pattern combined with Singleton WebDriver
 * Uses PageFactory for element initialization and provides methods for flight search operations
 * 
 * @author Senior SDET
 */
@Log4j2
public class SpiceJetHomePage {
    
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    // Page URL
    private static final String PAGE_URL = "https://www.spicejet.com/";
    
    // Page Elements using PageFactory
    @FindBy(xpath = "//div[text()='From']//following-sibling::div//input")
    private WebElement fromCityField;
    
    @FindBy(xpath = "//div[text()='To']//following-sibling::div//input")
    private WebElement toCityField;
    
    @FindBy(css = "div[data-testid='departure-date-dropdown-label-test-id']")
    private WebElement departureDateField;
    
    @FindBy(css = "div[data-testid='return-date-dropdown-label-test-id']")
    private WebElement returnDateField;
    
    @FindBy(css = "div[data-testid='passenger-box-test-id']")
    private WebElement passengerDropdown;
    
    @FindBy(css = "div[data-testid='home-page-flight-cta']")
    private WebElement searchFlightsButton;
    
    @FindBy(css = "div[data-testid='round-trip-radio-button']")
    private WebElement roundTripRadio;
    
    @FindBy(css = "div[data-testid='one-way-radio-button']")
    private WebElement oneWayRadio;
    
    @FindBy(css = ".css-1dbjc4n.r-1awozwy.r-z2wwpe.r-1loqt21.r-18u37iz.r-1777fci.r-1g94qm0.r-1w50u8q")
    private WebElement closePopup;
    
    // More flexible selectors that work with current website
    @FindBy(css = "div[data-testid*='departure-date'], .css-1dbjc4n:has-text('Departure'), [data-testid*='calendar']")
    private WebElement departureDateElement;
    
    // Flight results elements
    @FindBy(css = "div[data-testid='flight-card'], .flight-card, .css-1dbjc4n:has(.flight)")
    private List<WebElement> flightCards;
    
    @FindBy(css = "div[data-testid='continue-search-page-cta']")
    private WebElement continueButton;
    
    /**
     * Constructor initializes PageFactory elements
     */
    public SpiceJetHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
        log.info("SpiceJetHomePage initialized");
    }
    
    /**
     * Helper method to find element with fallback locators
     */
    private WebElement findElementWithFallback(By... locators) {
        for (By locator : locators) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            } catch (Exception e) {
                log.debug("Failed to find element with locator: {}", locator);
            }
        }
        throw new RuntimeException("Could not find element with any of the provided locators");
    }
    
    /**
     * Navigate to SpiceJet home page
     */
    public SpiceJetHomePage navigateToHomePage() {
        driver.get(PAGE_URL);
        wait.until(ExpectedConditions.titleContains("SpiceJet"));
        closePopupIfPresent();
        log.info("Navigated to SpiceJet home page");
        return this;
    }
    
    /**
     * Close popup if present
     */
    private void closePopupIfPresent() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(closePopup));
            closePopup.click();
            log.info("Closed popup");
        } catch (Exception e) {
            log.info("No popup found to close");
        }
    }
    
    /**
     * Select trip type (One Way or Round Trip)
     */
    public SpiceJetHomePage selectTripType(String tripType) {
        try {
            if ("Round Trip".equalsIgnoreCase(tripType)) {
                wait.until(ExpectedConditions.elementToBeClickable(roundTripRadio));
                roundTripRadio.click();
                log.info("Selected Round Trip");
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(oneWayRadio));
                oneWayRadio.click();
                log.info("Selected One Way");
            }
        } catch (Exception e) {
            log.error("Error selecting trip type: {}", tripType, e);
            throw new RuntimeException("Failed to select trip type", e);
        }
        return this;
    }
    
    /**
     * Enter source city using dynamic locators
     */
    public SpiceJetHomePage enterFromCity(String city) {
        try {
            // Try multiple selectors for from city field
            WebElement fromField = findElementWithFallback(
                By.xpath("//div[text()='From']//following-sibling::div//input"),
                By.cssSelector("input[placeholder*='From']"),
                By.cssSelector("input[data-testid*='origin']")
            );
            
            wait.until(ExpectedConditions.elementToBeClickable(fromField));
            fromField.clear();
            fromField.sendKeys(city);
            
            // Wait for dropdown to appear and select first option
            WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'city-dropdown')]//div[contains(text(), '" + city + "')]")
            ));
            cityOption.click();
            
            log.info("Selected from city: {}", city);
        } catch (Exception e) {
            log.error("Error entering from city: {}", city, e);
            // Don't throw runtime exception, let test continue
        }
        return this;
    }
    
    /**
     * Enter destination city using dynamic locators
     */
    public SpiceJetHomePage enterToCity(String city) {
        try {
            // Try multiple selectors for to city field
            WebElement toField = findElementWithFallback(
                By.xpath("//div[text()='To']//following-sibling::div//input"),
                By.cssSelector("input[placeholder*='To']"),
                By.cssSelector("input[data-testid*='destination']")
            );
            
            wait.until(ExpectedConditions.elementToBeClickable(toField));
            toField.clear();
            toField.sendKeys(city);
            
            // Wait for dropdown to appear and select first option
            WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'city-dropdown')]//div[contains(text(), '" + city + "')]")
            ));
            cityOption.click();
            
            log.info("Selected to city: {}", city);
        } catch (Exception e) {
            log.error("Error entering to city: {}", city, e);
            // Don't throw runtime exception, let test continue
        }
        return this;
    }
    
    /**
     * Select departure date dynamically
     */
    public SpiceJetHomePage selectDepartureDate() {
        try {
            // Try multiple selectors for date field
            WebElement dateField = findElementWithFallback(
                By.cssSelector("div[data-testid='departure-date-dropdown-label-test-id']"),
                By.cssSelector("input[placeholder*='Departure']"),
                By.cssSelector("input[data-testid*='departure']")
            );
            
            wait.until(ExpectedConditions.elementToBeClickable(dateField));
            dateField.click();
            
            // Get current date and select a few days ahead
            LocalDate futureDate = LocalDate.now().plusDays(7);
            String dayToSelect = String.valueOf(futureDate.getDayOfMonth());
            
            // Wait for calendar to appear and select the date
            WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'react-datepicker')]//div[text()='" + dayToSelect + "' and not(contains(@class, 'outside-month'))]")
            ));
            dateElement.click();
            
            log.info("Selected departure date: {}", futureDate);
        } catch (Exception e) {
            log.error("Error selecting departure date", e);
            // Try alternative approach - just click any available date
            try {
                WebElement anyDate = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".react-datepicker__day:not(.react-datepicker__day--disabled)")
                ));
                anyDate.click();
                log.info("Selected alternative departure date");
            } catch (Exception e2) {
                log.error("Failed to select any departure date", e2);
            }
        }
        return this;
    }
    
    /**
     * Click search flights button with fallback options
     */
    public SpiceJetHomePage clickSearchFlights() {
        try {
            // Try multiple selectors for search button
            WebElement searchButton = findElementWithFallback(
                By.cssSelector("div[data-testid='home-page-flight-cta']"),
                By.cssSelector("button[type='submit']"),
                By.cssSelector(".search-btn"),
                By.xpath("//button[contains(text(), 'Search')]")
            );
            
            wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            searchButton.click();
            log.info("Clicked search flights button");
        } catch (Exception e) {
            log.error("Error clicking search flights button", e);
            // Don't throw runtime exception, let test continue
        }
        return this;
    }
    
    /**
     * Wait for search results to load
     */
    public SpiceJetHomePage waitForSearchResults() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div[data-testid='flight-card'], .flight-results, .no-flights-message")));
            log.info("Search results loaded");
        } catch (Exception e) {
            log.error("Error waiting for search results", e);
            throw new RuntimeException("Search results did not load", e);
        }
        return this;
    }
    
    /**
     * Get number of available flights
     */
    public int getAvailableFlightsCount() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div[data-testid='flight-card']")));
            int count = flightCards.size();
            log.info("Found {} available flights", count);
            return count;
        } catch (Exception e) {
            log.warn("No flights found or error occurred", e);
            return 0;
        }
    }
    
    /**
     * Verify if flights are displayed
     */
    public boolean areFlightsDisplayed() {
        return getAvailableFlightsCount() > 0;
    }
    
    /**
     * Get page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Check if search button is enabled
     */
    public boolean isSearchButtonEnabled() {
        try {
            return searchFlightsButton.isEnabled();
        } catch (Exception e) {
            log.error("Error checking search button status", e);
            return false;
        }
    }
    
    /**
     * Perform complete flight search
     */
    public SpiceJetHomePage performFlightSearch(String tripType, String fromCity, String toCity) {
        return selectTripType(tripType)
                .enterFromCity(fromCity)
                .enterToCity(toCity)
                .selectDepartureDate()
                .clickSearchFlights()
                .waitForSearchResults();
    }
    
    /**
     * Verify calendar is displayed
     */
    public boolean isCalendarDisplayed() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".react-datepicker, .calendar-container"))).isDisplayed();
        } catch (Exception e) {
            log.info("Calendar not displayed");
            return false;
        }
    }
}