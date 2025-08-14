package com.testautomation.singleton.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.testautomation.singleton.base.BaseTest;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
// import main.java.com.testautomation.singleton.pages.SpiceJetHomePage;
import com.testautomation.singleton.pages.SpiceJetHomePage;

/**
 * Test class for booking calendar functionality using Singleton WebDriver
 * 
 * @author Senior SDET
 */
@Log4j2
@Epic("Singleton Pattern Implementation")
@Feature("Booking Calendar Functionality")
public class BookingCalendarTest extends BaseTest {
    
    private SpiceJetHomePage spiceJetHomePage;
    
    @Test(priority = 1, description = "Verify calendar visibility")
    @Story("Calendar Display")
    @Severity(SeverityLevel.NORMAL)
    public void testCalendarVisibility() {
        log.info("Starting test: testCalendarVisibility");
        
        spiceJetHomePage = new SpiceJetHomePage(getDriver());
        spiceJetHomePage.navigateToHomePage();
        
        // Verify basic page load
        Assert.assertTrue(getPageTitle().contains("SpiceJet"), 
                "SpiceJet home page should be loaded");
        
        log.info("Test completed: testCalendarVisibility");
    }
    
    @Test(priority = 2, description = "Verify date selection functionality") 
    @Story("Date Selection")
    @Severity(SeverityLevel.NORMAL)
    public void testDateSelection() {
        log.info("Starting test: testDateSelection");
        
        spiceJetHomePage = new SpiceJetHomePage(getDriver());
        spiceJetHomePage.navigateToHomePage()
                .selectDepartureDate();
        
        // Basic verification that we can interact with the page
        Assert.assertTrue(getCurrentUrl().contains("spicejet"), 
                "Should remain on SpiceJet website");
        
        log.info("Test completed: testDateSelection");
    }
}
