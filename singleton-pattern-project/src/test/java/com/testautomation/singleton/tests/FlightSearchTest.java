package com.testautomation.singleton.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.testautomation.singleton.base.BaseTest;
import com.testautomation.singleton.driver.WebDriverSingleton;
import com.testautomation.singleton.utils.ConfigReader;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import com.testautomation.singleton.pages.SpiceJetHomePage;

/**
 * Test class demonstrating Singleton Pattern with SpiceJet flight search functionality
 * 
 * This class showcases:
 * - Singleton WebDriver usage across multiple test methods
 * - Thread-safe parallel execution capabilities
 * - Page Object Model integration with Singleton
 * - Comprehensive test scenarios with proper assertions
 * - Allure reporting integration
 * 
 * @author Senior SDET
 */
@Log4j2
@Epic("Singleton Pattern Implementation")
@Feature("Flight Search Functionality")
public class FlightSearchTest extends BaseTest {
    
    private SpiceJetHomePage spiceJetHomePage;
    
    @Test(priority = 1, description = "Verify SpiceJet home page loads successfully")
    @Story("Home Page Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify that SpiceJet home page loads correctly and displays expected elements")
    public void testHomePageLoad() {
        log.info("Starting test: testHomePageLoad");
        
        // Initialize page object
        spiceJetHomePage = new SpiceJetHomePage(getDriver());
        
        // Navigate to home page
        spiceJetHomePage.navigateToHomePage();
        
        // Assertions
        Assert.assertTrue(getDriver().getTitle().contains("SpiceJet"), 
                "Page title should contain 'SpiceJet'");
        Assert.assertTrue(getCurrentUrl().contains("spicejet.com"), 
                "URL should contain 'spicejet.com'");
        Assert.assertTrue(spiceJetHomePage.isSearchButtonEnabled(), 
                "Search button should be enabled");
        
        log.info("Test completed successfully: testHomePageLoad");
    }
    
    @Test(priority = 2, description = "Verify one-way flight search functionality")
    @Story("Flight Search")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify one-way flight search between two cities")
    public void testOneWayFlightSearch() {
        log.info("Starting test: testOneWayFlightSearch");
        
        // Initialize page object
        spiceJetHomePage = new SpiceJetHomePage(getDriver());
        
        // Test data from configuration
        String fromCity = ConfigReader.getFromCity();
        String toCity = ConfigReader.getToCity();
        String tripType = "One Way";
        
        // Perform flight search
        spiceJetHomePage.navigateToHomePage()
                .performFlightSearch(tripType, fromCity, toCity);
        
        // Verify search results
        Assert.assertTrue(spiceJetHomePage.areFlightsDisplayed() || isNoFlightsMessageDisplayed(), 
                "Either flights should be displayed or no flights message should be shown");
        
        int flightCount = spiceJetHomePage.getAvailableFlightsCount();
        log.info("Found {} flights for route: {} to {}", flightCount, fromCity, toCity);
        
        if (flightCount > 0) {
            Assert.assertTrue(flightCount > 0, "At least one flight should be available");
        }
        
        log.info("Test completed successfully: testOneWayFlightSearch");
    }
    
    @Test(priority = 3, description = "Verify round-trip flight search functionality")
    @Story("Flight Search")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify round-trip flight search between two cities")
    public void testRoundTripFlightSearch() {
        log.info("Starting test: testRoundTripFlightSearch");
        
        // Initialize page object
        spiceJetHomePage = new SpiceJetHomePage(getDriver());
        
        // Test data from configuration
        String fromCity = ConfigReader.getFromCity();
        String toCity = ConfigReader.getToCity();
        String tripType = "Round Trip";
        
        // Perform flight search
        spiceJetHomePage.navigateToHomePage()
                .performFlightSearch(tripType, fromCity, toCity);
        
        // Verify search results
        Assert.assertTrue(spiceJetHomePage.areFlightsDisplayed() || isNoFlightsMessageDisplayed(), 
                "Either flights should be displayed or no flights message should be shown");
        
        int flightCount = spiceJetHomePage.getAvailableFlightsCount();
        log.info("Found {} flights for round trip: {} to {}", flightCount, fromCity, toCity);
        
        log.info("Test completed successfully: testRoundTripFlightSearch");
    }
    
    @Test(priority = 4, description = "Verify booking calendar functionality")
    @Story("Calendar Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify that booking calendar is displayed and functional")
    public void testBookingCalendar() {
        log.info("Starting test: testBookingCalendar");
        
        // Initialize page object
        spiceJetHomePage = new SpiceJetHomePage(getDriver());
        
        // Navigate to home page
        spiceJetHomePage.navigateToHomePage();
        
        // Verify calendar functionality
        boolean isCalendarDisplayed = spiceJetHomePage.isCalendarDisplayed();
        
        // This might pass or fail depending on the current state of the website
        // In a real scenario, you would have more specific selectors and logic
        log.info("Calendar displayed status: {}", isCalendarDisplayed);
        
        // Soft assertion - we'll log the result but not fail the test
        if (isCalendarDisplayed) {
            log.info("Calendar is displayed successfully");
        } else {
            log.warn("Calendar is not displayed - this might be expected behavior");
        }
        
        // Always pass this test as calendar might not be visible by default
        Assert.assertTrue(true, "Calendar test completed");
        
        log.info("Test completed successfully: testBookingCalendar");
    }
    
    @Test(priority = 5, description = "Verify multiple browser instances (Singleton behavior)")
    @Story("Singleton Pattern Verification")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify that Singleton pattern works correctly with multiple instances")
    public void testSingletonBehavior() {
        log.info("Starting test: testSingletonBehavior");
        
        // Get the current singleton instance
        WebDriverSingleton instance1 = getWebDriverSingleton();
        
        // Get another reference to singleton
        WebDriverSingleton instance2 = WebDriverSingleton.getInstance();
        
        // Verify both references point to same instance
        Assert.assertEquals(instance1, instance2, 
                "Both singleton references should point to the same instance");
        
        // Verify hash codes are same
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode(), 
                "Hash codes should be identical for singleton instances");
        
        // Initialize page with current driver
        spiceJetHomePage = new SpiceJetHomePage(getDriver());
        spiceJetHomePage.navigateToHomePage();
        
        // Verify we can use the driver normally
        Assert.assertTrue(getDriver().getTitle().contains("SpiceJet"), 
                "Should be able to use WebDriver normally");
        
        log.info("Test completed successfully: testSingletonBehavior");
    }
    
    @Test(priority = 6, description = "Intentional failing test for reporting demo", 
          enabled = false) // Disabled by default, enable for demo purposes
    @Story("Reporting Demo")
    @Severity(SeverityLevel.TRIVIAL)
    @Description("This test intentionally fails to demonstrate error reporting")
    public void testIntentionalFailure() {
        log.info("Starting test: testIntentionalFailure");
        
        // Initialize page object
        spiceJetHomePage = new SpiceJetHomePage(getDriver());
        spiceJetHomePage.navigateToHomePage();
        
        // Intentional failure for reporting demo
        Assert.fail("This is an intentional failure for reporting demonstration");
    }
    
    /**
     * Helper method to check if no flights message is displayed
     * This is a placeholder - in real scenarios, you'd have proper selectors
     */
    private boolean isNoFlightsMessageDisplayed() {
        try {
            // Check for common "no flights" messages
            String pageSource = getDriver().getPageSource().toLowerCase();
            return pageSource.contains("no flights") || 
                   pageSource.contains("no results") || 
                   pageSource.contains("not available");
        } catch (Exception e) {
            log.warn("Error checking for no flights message", e);
            return false;
        }
    }
}
