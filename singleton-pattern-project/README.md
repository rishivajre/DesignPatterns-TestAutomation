# Singleton Pattern in Test Automation

## Pattern Overview

The Singleton Pattern ensures that a class has only one instance and provides a global point of access to that instance. In test automation, this is commonly used for WebDriver management to avoid resource waste and conflicts.

## Problem Statement

- Multiple WebDriver instances consume excessive memory and system resources
- Parallel test execution can create driver conflicts
- Need thread-safe WebDriver management
- Protection against common attack vectors (cloning, serialization, reflection)

## Implementation Strategy

### 1. Thread-Safe Singleton with Double-Checked Locking
```java
public class WebDriverSingleton {
    private static volatile WebDriverSingleton instance;
    private ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    
    private WebDriverSingleton() {
        // Private constructor prevents instantiation
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to create instance");
        }
    }
    
    public static WebDriverSingleton getInstance() {
        if (instance == null) {
            synchronized (WebDriverSingleton.class) {
                if (instance == null) {
                    instance = new WebDriverSingleton();
                }
            }
        }
        return instance;
    }
}
```

### 2. Anti-Pattern Protections
- **Cloning Protection**: Override `clone()` method
- **Serialization Protection**: Implement `readResolve()` method
- **Reflection Protection**: Check instance in constructor

## Test Site: SpiceJet
- **URL**: https://www.spicejet.com/
- **Test Scenarios**:
  - Search flights between cities
  - Verify available flight listings
  - Validate booking calendar functionality
  - Test responsive design elements

## Key Features

1. **Thread-Safe WebDriver Management**: Each thread gets its own WebDriver instance
2. **Resource Optimization**: Single manager instance across application
3. **Configuration-Driven**: Browser type from config.properties
4. **Parallel Test Support**: ThreadLocal ensures no conflicts
5. **Comprehensive Protection**: Against all singleton breaking patterns

## Configuration

```properties
# Browser Configuration
browser=chrome
headless=false
remote.execution=false

# WebDriver Configuration  
implicit.wait=10
explicit.wait=20
page.load.timeout=30

# SpiceJet Specific
spicejet.url=https://www.spicejet.com/
spicejet.from.city=Delhi
spicejet.to.city=Mumbai
```

## Execution Commands

```bash
# Run all singleton pattern tests
mvn clean test -Dtest=**/singleton/**/*Test

# Run with specific browser
mvn clean test -Dtest=**/singleton/**/*Test -Dbrowser=firefox

# Run in headless mode
mvn clean test -Dtest=**/singleton/**/*Test -Dheadless=true

# Generate reports
mvn allure:serve
```

## Learning Objectives

### For Senior SDET Interviews

**Q: Why use Singleton for WebDriver?**
A: Resource optimization, thread safety in parallel execution, centralized configuration management, and prevention of driver conflicts.

**Q: How do you handle parallel execution with Singleton?**
A: Use ThreadLocal to maintain separate WebDriver instances per thread while keeping the manager singleton.

**Q: What are the risks of Singleton pattern?**
A: Tight coupling, difficulty in testing, global state issues, and potential memory leaks if not properly managed.

**Q: How do you make Singleton thread-safe?**
A: Double-checked locking, synchronized methods, or enum-based implementation for better performance and safety.

## Best Practices

1. Use ThreadLocal for WebDriver instances in parallel execution
2. Implement proper cleanup in quit() methods
3. Handle browser crashes gracefully with retry mechanisms
4. Use configuration files for browser selection
5. Implement comprehensive logging for debugging

## Anti-Patterns to Avoid

1. Creating multiple WebDriver instances unnecessarily
2. Not handling thread safety in parallel execution
3. Forgetting to quit WebDriver instances (memory leaks)
4. Hard-coding browser configurations
5. Not implementing singleton breaking protections

## Advanced Implementation

The implementation includes:
- Enum-based singleton for better thread safety
- Custom WebDriver wrapper with additional capabilities
- Retry mechanisms for browser failures
- Integration with Selenium Grid and cloud platforms
- Comprehensive test coverage for all protection mechanisms
