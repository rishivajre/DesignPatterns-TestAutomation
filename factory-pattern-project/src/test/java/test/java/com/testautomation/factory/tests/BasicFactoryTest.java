package test.java.com.testautomation.factory.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import lombok.extern.log4j.Log4j2;

/**
 * Basic test class for Factory Pattern project
 * 
 * @author Senior SDET
 */
@Log4j2
public class BasicFactoryTest {
    
    @Test
    public void testFactoryPatternSetup() {
        log.info("Factory Pattern project setup test");
        Assert.assertTrue(true, "Factory pattern project is properly configured");
    }
}
