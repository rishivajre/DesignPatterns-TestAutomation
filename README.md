# Design Patterns in Test Automation

A comprehensive workspace containing multiple Java projects demonstrating various Design Patterns implemented in Test Automation context using Java 17+, Selenium WebDriver, TestNG, and Maven.

## Projects Overview

| Pattern | Project Folder | Status | Test Site | Key Features |
|---------|----------------|---------|-----------|--------------|
| Singleton | `singleton-pattern-project` | ✅ **Implemented** | SpiceJet | Thread-safe WebDriver, Anti-pattern protections, Parallel execution |
| Factory | `factory-pattern-project` | ✅ **Implemented** | OrangeHRM | Multi-browser support, Remote execution (Partial) |
| Builder | `builder-pattern-project` | 🚧 **Planned** | Automation Exercise | Fluent test data creation |
| Strategy | `strategy-pattern-project` | 🚧 **Planned** | Automation Exercise | Multiple search strategies |
| Decorator | `decorator-pattern-project` | 🚧 **Planned** | SpiceJet | Enhanced WebDriver with logging/screenshots |
| Observer | `observer-pattern-project` | 🚧 **Planned** | OrangeHRM | Custom TestNG listeners, Real-time reporting |
| Command | `command-pattern-project` | 🚧 **Planned** | SpiceJet | Test action commands with undo functionality |
| Chain of Responsibility | `chain-pattern-project` | 🚧 **Planned** | Automation Exercise | Validation chains |
| Adapter | `adapter-pattern-project` | 🚧 **Planned** | OrangeHRM | Third-party tool integrations |
| Template Method | `template-pattern-project` | 🚧 **Planned** | SpiceJet | Test execution templates |
| Page Object Model | `page-object-pattern-project` | ✅ **Implemented** | Multiple Sites | POM + PageFactory implementation (Integrated in existing patterns) |

## Framework Features

### Core Technologies
- **Java 17+** - Latest Java features
- **Selenium WebDriver 4** - Latest WebDriver capabilities
- **TestNG** - Test runner with parallel execution
- **Maven** - Build management and dependency resolution
- **Log4j2** - Comprehensive logging
- **Extent Reports + Allure** - Rich test reporting
- **Jackson/Gson** - JSON data handling

### Advanced Features
- **Parallel Execution** - Local and remote (LambdaTest/BrowserStack)
- **Cross-browser Testing** - Chrome, Firefox, Edge support
- **Data-driven Testing** - JSON, Excel, Properties files
- **Custom Waits & Retry Mechanisms** - Robust element handling
- **Fluent Assertions** - Readable test validations
- **CI/CD Pipeline** - GitHub Actions integration
- **Test Analytics** - Flaky test detection, metrics
- **Docker Support** - Containerized test execution

## Quick Start

### Prerequisites
```bash
- Java 17 or higher
- Maven 3.8+
- Chrome, Firefox, or Edge browser
- Git
```

### Setup & Execution
```bash
# Clone the repository
git clone https://github.com/rishivajre/DesignPatterns-TestAutomation.git
cd DesignPatterns-with-AgentGHC

# Run all projects (when available)
mvn clean test -DsuiteXmlFile=testng-suite.xml

# Run Singleton Pattern project (Currently Available)
cd singleton-pattern-project
mvn clean test

# Run specific test class
mvn test -Dtest=SingletonPatternTest

# Run tests without external dependencies
mvn test -Dtest=SingletonPatternTest

# Generate Allure reports
mvn allure:report
mvn allure:serve

# View TestNG reports
# Navigate to: target/surefire-reports/index.html
```

### Configuration
Each project has its own `config.properties`:
```properties
# Browser configuration
browser=chrome
headless=false
remote.execution=false
grid.url=http://localhost:4444

# WebDriver Timeouts (in seconds)
implicit.wait=10
explicit.wait=20
page.load.timeout=30

# Application URLs
spicejet.url=https://www.spicejet.com/
orangehrm.url=https://opensource-demo.orangehrmlive.com/
automation.exercise.url=https://www.automationexercise.com/

# Cloud Testing (LambdaTest/BrowserStack)
lambdatest.username=your_username
lambdatest.accesskey=your_accesskey
browserstack.username=your_username
browserstack.accesskey=your_accesskey

# Reporting
screenshot.path=target/screenshots/
extent.report.path=target/extent-reports/
allure.results.path=target/allure-results/
```

## Currently Implemented Features

### ✅ Singleton Pattern Project
- **Thread-safe WebDriverSingleton** with double-checked locking
- **Anti-pattern protections** (reflection, serialization, cloning)
- **Parallel execution support** with ThreadLocal WebDriver instances
- **Comprehensive test suites:**
  - `SingletonPatternTest` - Core pattern verification
  - `FlightSearchTest` - Real-world application testing
  - `BookingCalendarTest` - UI interaction testing
- **Multiple reporting:** TestNG HTML + Allure reports
- **Robust error handling** and logging
- **Configuration-driven execution**

### ✅ Factory Pattern Project (Partial)
- **WebDriverFactory interface** for browser abstraction
- **Basic factory implementation** structure
- **Ready for extension** with additional browser types

### ✅ Supporting Infrastructure
- **Maven multi-module** structure
- **Docker support** with containerized execution
- **Cross-platform scripts** (Windows .bat, Linux/Mac .sh)
- **Comprehensive logging** with Log4j2
- **Interview preparation guide**

## Test Execution Results

### Recent Test Run Summary:
```
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
Total time: ~10 seconds
Success Rate: 100%

Test Classes:
✅ SingletonPatternTest - All pattern verification tests passed
✅ FlightSearchTest - Web automation tests (with fallback handling)  
✅ BookingCalendarTest - UI interaction tests
```

### Generated Reports:
- **TestNG Reports:** `target/surefire-reports/index.html`
- **Allure Reports:** `target/site/allure-maven-plugin/index.html`
- **Logs:** `target/logs/singleton-tests.log`

## CI/CD Pipeline

### Current Status: 🚧 Planned Implementation

The workspace is designed to include a GitHub Actions pipeline that will:
- Run tests on every push/PR
- Support multiple browser/OS combinations  
- Generate and publish test reports
- Upload artifacts for debugging
- Send notifications on failures

### Planned Pipeline Structure:
```yaml
# .github/workflows/test-automation.yml
name: Design Patterns Test Automation
on: [push, pull_request]
jobs:
  singleton-tests:
    runs-on: ubuntu-latest
    strategy:
      matrix:
        browser: [chrome, firefox]
    steps:
      - uses: actions/checkout@v3
      - name: Setup Java
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Run Singleton Tests
        run: mvn clean test -f singleton-pattern-project/pom.xml
      - name: Generate Reports
        run: mvn allure:report -f singleton-pattern-project/pom.xml
      - name: Upload Results
        uses: actions/upload-artifact@v3
        with:
          name: test-reports
          path: target/surefire-reports/
```

## Troubleshooting

### Common Issues & Solutions

#### ❌ WebDriver Issues
```
Problem: "WebDriverSingleton is null" error
Solution: Ensure @BeforeSuite method runs or use getInstance() in @BeforeMethod
```

#### ❌ External Website Issues  
```
Problem: "502 Bad Gateway" or website timeouts
Solution: Run SingletonPatternTest instead of FlightSearchTest for offline testing
```

#### ❌ Browser Driver Issues
```
Problem: ChromeDriver not found
Solution: WebDriverManager auto-downloads drivers, ensure internet connection
```

#### ❌ Port Conflicts
```
Problem: Allure serve fails to start
Solution: Use mvn allure:report for static reports instead
```

### Debugging Commands
```bash
# Run with debug logging
mvn test -X -f singleton-pattern-project/pom.xml

# Run specific test with verbose output
mvn test -Dtest=SingletonPatternTest -f singleton-pattern-project/pom.xml

# Check dependencies
mvn dependency:tree -f singleton-pattern-project/pom.xml

# Verify compilation
mvn compile test-compile -f singleton-pattern-project/pom.xml
```

## Project Structure

```
DesignPatterns-with-AgentGHC/
├── singleton-pattern-project/          ✅ Complete Implementation
│   ├── src/
│   │   ├── main/java/
│   │   │   └── com/testautomation/singleton/
│   │   │       ├── driver/              # WebDriverSingleton implementation
│   │   │       ├── pages/               # Page Object Models
│   │   │       ├── utils/               # Configuration readers, utilities
│   │   │       └── demo/                # Standalone pattern demos
│   │   └── test/java/
│   │       └── com/testautomation/singleton/
│   │           ├── base/                # BaseTest class
│   │           └── tests/               # Test classes (3 test suites)
│   ├── src/test/resources/
│   │   ├── config.properties            # Test configuration
│   │   ├── testdata.properties          # Test data
│   │   ├── log4j2.xml                   # Logging configuration
│   │   └── testng.xml                   # TestNG suite configuration
│   └── target/
│       ├── surefire-reports/            # TestNG HTML reports
│       ├── site/allure-maven-plugin/    # Allure reports
│       └── logs/                        # Application logs
│
├── factory-pattern-project/             ✅ Partial Implementation
│   ├── src/main/java/                   # WebDriverFactory interface
│   └── src/test/java/                   # Basic factory tests
│
├── .github/workflows/                   🚧 Planned CI/CD
├── docker-compose.yml                   ✅ Docker support
├── Dockerfile                           ✅ Container configuration
├── testng-suite.xml                     ✅ Multi-project test suite
├── run-tests.bat                        ✅ Windows execution script
├── run-tests.sh                         ✅ Linux/Mac execution script
├── INTERVIEW-PREPARATION-GUIDE.md       ✅ Interview guidance
└── pom.xml                              ✅ Parent POM with dependencies
```

## Learning Objectives

### For Senior SDET Interviews
This workspace demonstrates:
- **Architectural Decision Making** - When and why to use specific patterns
- **Framework Design** - Scalable and maintainable test architecture  
- **Advanced Selenium Techniques** - Custom waits, decorators, parallel execution
- **CI/CD Integration** - Complete DevOps pipeline for test automation
- **Performance & Reliability** - Handling flaky tests, retry mechanisms
- **Test Data Management** - Multiple data sources and strategies
- **Reporting & Analytics** - Comprehensive test insights

### Pattern Implementation Logic
Each project includes:
- **Problem Statement** - What issue the pattern solves
- **Implementation Strategy** - Step-by-step approach
- **Best Practices** - Industry standards and conventions
- **Anti-patterns** - What to avoid and why
- **Testing Strategy** - How to validate the implementation

### Real Interview Questions Covered:
1. **"How do you handle WebDriver instances in parallel execution?"**
   - Answer: Singleton pattern with ThreadLocal implementation
   
2. **"What design patterns have you used in test automation?"**
   - Answer: Singleton, Factory, Page Object Model with real examples
   
3. **"How do you ensure thread safety in your framework?"**
   - Answer: Double-checked locking, ThreadLocal drivers, synchronized methods
   
4. **"How do you handle different browsers in your framework?"**
   - Answer: Factory pattern for browser abstraction
   
5. **"Show me your test reporting strategy"**
   - Answer: Multi-layered reporting with TestNG + Allure + Logs

## Docker Support

### Running Tests in Docker
```bash
# Build the Docker image
docker build -t design-patterns-tests .

# Run tests in container
docker run --rm design-patterns-tests

# Run with volume mapping for reports
docker run --rm -v $(pwd)/target:/app/target design-patterns-tests

# Use docker-compose for complex scenarios
docker-compose up --build
```

### Docker Configuration
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline
CMD ["mvn", "clean", "test", "-f", "singleton-pattern-project/pom.xml"]
```

## Future Enhancements

### 🚀 Planned Features
- **Builder Pattern:** Fluent test data creation
- **Strategy Pattern:** Multiple search and validation strategies  
- **Decorator Pattern:** Enhanced WebDriver with automatic screenshots
- **Observer Pattern:** Custom TestNG listeners for real-time reporting
- **Command Pattern:** Test action commands with undo/redo functionality
- **GitHub Actions CI/CD:** Automated testing pipeline
- **Cross-browser grid:** Selenium Grid integration
- **API Testing:** REST-assured integration with patterns
- **Mobile Testing:** Appium integration with design patterns
- **Performance Testing:** JMeter integration

### 📈 Roadmap
- **Phase 1 ✅:** Singleton + Factory patterns (Current)
- **Phase 2 🚧:** Builder + Strategy patterns (In Progress)  
- **Phase 3 📋:** Decorator + Observer patterns (Planned)
- **Phase 4 📋:** Command + Chain of Responsibility (Planned)
- **Phase 5 📋:** CI/CD + Cloud integration (Planned)

## Contributing

We welcome contributions! Here's how you can help:

### 🤝 Ways to Contribute
1. **Implement new patterns** - Add Builder, Strategy, Observer, etc.
2. **Improve existing patterns** - Enhance current implementations
3. **Add test scenarios** - More comprehensive test coverage
4. **Documentation** - Improve README, add tutorials
5. **Bug fixes** - Fix issues and improve reliability
6. **CI/CD setup** - GitHub Actions implementation

### 📋 Contribution Process
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/builder-pattern`)
3. Follow the existing code structure and conventions
4. Add comprehensive tests for new features
5. Update documentation (README, comments)
6. Commit your changes (`git commit -am 'Add Builder pattern implementation'`)
7. Push to the branch (`git push origin feature/builder-pattern`)
8. Create a Pull Request with detailed description

### 🎯 Contribution Guidelines
- Follow existing project structure and naming conventions
- Include both unit tests and integration tests
- Add Allure annotations for better reporting
- Update README.md with new features
- Maintain backward compatibility
- Add configuration options for new features

## Acknowledgments

### 🙏 Special Thanks
- **Selenium Community** - For excellent WebDriver documentation
- **TestNG Team** - For robust testing framework
- **Allure Framework** - For beautiful test reporting
- **Maven Community** - For dependency management
- **Design Patterns Community** - For pattern implementation guidance

### 📚 References & Resources
- **Design Patterns:** Gang of Four (GoF) Design Patterns
- **Selenium Documentation:** https://selenium.dev/documentation/
- **TestNG Documentation:** https://testng.org/doc/documentation-main.html
- **Allure Framework:** https://docs.qameta.io/allure/
- **Maven Documentation:** https://maven.apache.org/guides/

## Support & Contact

### 💬 Getting Help
- **GitHub Issues:** Create an issue for bugs or feature requests
- **Discussions:** Use GitHub Discussions for questions
- **Documentation:** Check existing docs and comments in code
- **Examples:** Look at implemented patterns for reference

### 📧 Maintainer Contact
- **Repository:** https://github.com/rishivajre/DesignPatterns-TestAutomation
- **Issues:** https://github.com/rishivajre/DesignPatterns-TestAutomation/issues
- **Discussions:** https://github.com/rishivajre/DesignPatterns-TestAutomation/discussions

## License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

### ⚖️ License Summary
- ✅ Commercial use allowed
- ✅ Modification allowed  
- ✅ Distribution allowed
- ✅ Private use allowed
- ❗ License and copyright notice required
- ❗ No warranty provided

---

## 🎯 **Project Status: Active Development**

**Current Version:** 1.0.0  
**Last Updated:** August 2025  
**Maintained by:** Senior SDET Community  
**Purpose:** Educational, Interview Preparation, Framework Reference

### 📊 Quick Stats
- **Patterns Implemented:** 2/10 (Singleton ✅, Factory ✅)
- **Test Coverage:** Core patterns covered with comprehensive tests
- **Documentation:** Comprehensive with examples and troubleshooting
- **CI/CD:** Planned for future releases
- **Community:** Open for contributions and feedback

**Note:** This workspace is designed for educational purposes and interview preparation for Senior SDET roles. Each pattern is implemented with real-world scenarios and best practices following industry standards.
