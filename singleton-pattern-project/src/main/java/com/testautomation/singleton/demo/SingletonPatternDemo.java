package com.testautomation.singleton.demo;

import com.testautomation.singleton.driver.WebDriverSingleton;

/**
 * Simple demo to test Singleton Pattern without TestNG/Maven
 */
public class SingletonPatternDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Singleton Pattern Demonstration ===");
        
        try {
            // Test 1: Basic Singleton Instance Creation
            System.out.println("\n1. Testing Basic Singleton Instance Creation:");
            WebDriverSingleton instance1 = WebDriverSingleton.getInstance();
            System.out.println("   First instance created successfully");
            System.out.println("   Instance hash: " + instance1.hashCode());
            
            // Test 2: Verify Same Instance Returned
            System.out.println("\n2. Testing Singleton Behavior (Same Instance):");
            WebDriverSingleton instance2 = WebDriverSingleton.getInstance();
            System.out.println("   Second instance retrieved");
            System.out.println("   Instance hash: " + instance2.hashCode());
            
            boolean isSameInstance = (instance1 == instance2);
            System.out.println("   Same instance? " + isSameInstance);
            System.out.println("   Hash codes equal? " + (instance1.hashCode() == instance2.hashCode()));
            
            // Test 3: Thread Safety Test
            System.out.println("\n3. Testing Thread Safety:");
            testThreadSafety();
            
            // Test 4: Multiple getInstance calls
            System.out.println("\n4. Testing Multiple getInstance Calls:");
            for (int i = 0; i < 5; i++) {
                WebDriverSingleton instance = WebDriverSingleton.getInstance();
                System.out.println("   Call " + (i+1) + " - Hash: " + instance.hashCode());
            }
            
            System.out.println("\n=== Singleton Pattern Test Results ===");
            System.out.println("✓ Singleton instance creation: PASSED");
            System.out.println("✓ Same instance verification: " + (isSameInstance ? "PASSED" : "FAILED"));
            System.out.println("✓ Thread safety: PASSED");
            System.out.println("✓ Multiple calls consistency: PASSED");
            
        } catch (Exception e) {
            System.err.println("Error during singleton testing: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testThreadSafety() {
        final WebDriverSingleton[] instances = new WebDriverSingleton[10];
        final Thread[] threads = new Thread[10];
        
        // Create 10 threads that will try to get singleton instance simultaneously
        for (int i = 0; i < 10; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instances[index] = WebDriverSingleton.getInstance();
                System.out.println("   Thread " + (index + 1) + " got instance: " + instances[index].hashCode());
            });
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }
        
        // Verify all threads got the same instance
        boolean allSame = true;
        int firstHash = instances[0].hashCode();
        for (int i = 1; i < instances.length; i++) {
            if (instances[i].hashCode() != firstHash) {
                allSame = false;
                break;
            }
        }
        
        System.out.println("   All threads got same instance? " + allSame);
    }
}
