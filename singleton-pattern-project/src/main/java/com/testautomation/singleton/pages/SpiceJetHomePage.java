package com.testautomation.singleton.pages;

import java.time.Duration;
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
    
    @FindBy(css = "div[data-testid='undefined-month-April-2024'] div[data-testid='undefined-calendar-day-15']")
    private WebElement selectDate;
    
    @FindBy(xpath = "//div[contains(@class, 'css-76zvg2') and contains(text(), 'DEL')]")
    private WebElement selectFromCity;
    
    @FindBy(xpath = "//div[contains(@class, 'css-76zvg2') and contains(text(), 'BOM')]")
    private WebElement selectToCity;
    
    // Flight results elements
    @FindBy(css = "div[data-testid='flight-card']")
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
     * Enter source city
     */
    public SpiceJetHomePage enterFromCity(String city) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(fromCityField));
            fromCityField.clear();
            fromCityField.sendKeys(city);
            
            // Wait for dropdown and select city
            wait.until(ExpectedConditions.elementToBeClickable(selectFromCity));
            selectFromCity.click();
            
            log.info("Selected from city: {}", city);
        } catch (Exception e) {
            log.error("Error entering from city: {}", city, e);
            throw new RuntimeException("Failed to enter from city", e);
        }
        return this;
    }
    
    /**
     * Enter destination city
     */
    public SpiceJetHomePage enterToCity(String city) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(toCityField));
            toCityField.clear();
            toCityField.sendKeys(city);
            
            // Wait for dropdown and select city
            wait.until(ExpectedConditions.elementToBeClickable(selectToCity));
            selectToCity.click();
            
            log.info("Selected to city: {}", city);
        } catch (Exception e) {
            log.error("Error entering to city: {}", city, e);
            throw new RuntimeException("Failed to enter to city", e);
        }
        return this;
    }
    
    /**
     * Select departure date
     */
    public SpiceJetHomePage selectDepartureDate() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(departureDateField));
            departureDateField.click();
            
            // Select a future date (example: 15th of current month)
            wait.until(ExpectedConditions.elementToBeClickable(selectDate));
            selectDate.click();
            
            log.info("Selected departure date");
        } catch (Exception e) {
            log.error("Error selecting departure date", e);
            throw new RuntimeException("Failed to select departure date", e);
        }
        return this;
    }
    
    /**
     * Click search flights button
     */
    public SpiceJetHomePage clickSearchFlights() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(searchFlightsButton));
            searchFlightsButton.click();
            log.info("Clicked search flights button");
        } catch (Exception e) {
            log.error("Error clicking search flights button", e);
            throw new RuntimeException("Failed to click search flights", e);
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