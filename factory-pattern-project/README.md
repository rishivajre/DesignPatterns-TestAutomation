# Factory Pattern in Test Automation

## Pattern Overview

The Factory Pattern provides an interface for creating objects without exposing the instantiation logic to the client. In test automation, this is commonly used for WebDriver initialization supporting multiple browsers and execution environments.

## Problem Statement

- Hard-coded browser initialization makes switching browsers difficult
- Need to support local and remote execution without code changes
- Different environments require different WebDriver configurations
- Complex WebDriver setup logic scattered across test classes

## Implementation Strategy

### 1. Abstract Factory Interface
```java
public interface WebDriverFactory {
    WebDriver createDriver();
    WebDriver createRemoteDriver(String gridUrl);
}
```

### 2. Concrete Factory Implementations
- **ChromeDriverFactory**: Creates Chrome WebDriver instances
- **FirefoxDriverFactory**: Creates Firefox WebDriver instances  
- **EdgeDriverFactory**: Creates Edge WebDriver instances
- **RemoteDriverFactory**: Creates RemoteWebDriver for Grid execution

### 3. Factory Manager
- Central point for factory selection based on configuration
- Supports both local and remote execution
- Environment-specific configurations

## Test Site: OrangeHRM
- **URL**: https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
- **Test Scenarios**:
  - User login with valid/invalid credentials
  - Dashboard elements verification
  - User logout functionality
  - Cross-browser compatibility testing

## Key Features

1. **Multi-Browser Support**: Chrome, Firefox, Edge without code changes
2. **Environment Flexibility**: Local, Selenium Grid, Cloud platforms
3. **Configuration-Driven**: Browser selection from properties
4. **Centralized Logic**: All WebDriver creation logic in one place
5. **Easy Extension**: Simple to add new browsers or capabilities

## Configuration

```properties
# Browser Factory Configuration
browser.factory=chrome
local.execution=true
remote.grid.url=http://localhost:4444

# Cloud Platform Configuration
cloud.provider=lambdatest
lambdatest.username=your_username
lambdatest.accesskey=your_accesskey

# Browser Capabilities
chrome.headless=false
firefox.headless=false
edge.headless=false
```

## Learning Objectives

### For Senior SDET Interviews

**Q: When would you use Factory Pattern in test automation?**
A: When you need to create WebDriver instances dynamically based on runtime parameters like browser type, environment, or test requirements.

**Q: How does Factory Pattern differ from Singleton?**  
A: Factory creates new instances based on parameters, while Singleton ensures only one instance exists. They can be combined for optimal resource management.

**Q: How do you handle browser-specific capabilities?**
A: Each concrete factory handles its browser-specific options (headless mode, window size, extensions) while maintaining a consistent interface.

## Best Practices

1. Use abstract factories for families of related objects
2. Keep factory logic separate from business logic
3. Make factories configurable through external properties
4. Handle browser-specific capabilities in concrete factories
5. Implement proper error handling for unsupported browsers

## Execution Commands

```bash
# Run with Chrome (default)
mvn clean test

# Run with Firefox
mvn clean test -Dbrowser.factory=firefox

# Run on Selenium Grid  
mvn clean test -Dlocal.execution=false

# Run with specific capabilities
mvn clean test -Dchrome.headless=true
```
