# Design Patterns in Test Automation

A comprehensive workspace containing multiple Java projects demonstrating various Design Patterns implemented in Test Automation context using Java 17+, Selenium WebDriver, TestNG, and Maven.

## Projects Overview

| Pattern | Project Folder | Test Site | Key Features |
|---------|----------------|-----------|--------------|
| Singleton | `singleton-pattern-project` | SpiceJet | Thread-safe WebDriver, Anti-pattern protections |
| Factory | `factory-pattern-project` | OrangeHRM | Multi-browser support, Remote execution |
| Builder | `builder-pattern-project` | Automation Exercise | Fluent test data creation |
| Strategy | `strategy-pattern-project` | Automation Exercise | Multiple search strategies |
| Decorator | `decorator-pattern-project` | SpiceJet | Enhanced WebDriver with logging/screenshots |
| Observer | `observer-pattern-project` | OrangeHRM | Custom TestNG listeners, Real-time reporting |
| Command | `command-pattern-project` | SpiceJet | Test action commands with undo functionality |
| Chain of Responsibility | `chain-pattern-project` | Automation Exercise | Validation chains |
| Adapter | `adapter-pattern-project` | OrangeHRM | Third-party tool integrations |
| Template Method | `template-pattern-project` | SpiceJet | Test execution templates |
| Page Object Model | `page-object-pattern-project` | OrangeHRM | POM + PageFactory implementation |

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
git clone <repository-url>
cd DesignPatterns-with-AgentGHC

# Run all projects
mvn clean test -DsuiteXmlFile=testng-suite.xml

# Run specific pattern project
cd singleton-pattern-project
mvn clean test

# Generate reports
mvn allure:serve
```

### Configuration
Each project has its own `config.properties`:
```properties
# Browser configuration
browser=chrome
headless=false
remote.execution=false
grid.url=http://localhost:4444

# LambdaTest configuration
lt.username=your_username
lt.accesskey=your_accesskey

# Test configuration
implicit.wait=10
explicit.wait=20
page.load.timeout=30
```

## CI/CD Pipeline

The workspace includes a GitHub Actions pipeline that:
- Runs tests on every push/PR
- Supports multiple browser/OS combinations
- Generates and publishes test reports
- Uploads artifacts for debugging
- Sends notifications on failures

## Project Structure

```
DesignPatterns-with-AgentGHC/
├── singleton-pattern-project/
├── factory-pattern-project/
├── builder-pattern-project/
├── strategy-pattern-project/
├── decorator-pattern-project/
├── observer-pattern-project/
├── command-pattern-project/
├── chain-pattern-project/
├── adapter-pattern-project/
├── template-pattern-project/
├── page-object-pattern-project/
├── .github/workflows/
├── docker-compose.yml
├── testng-suite.xml
└── pom.xml (parent)
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

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-pattern`)
3. Commit your changes (`git commit -am 'Add new pattern implementation'`)
4. Push to the branch (`git push origin feature/new-pattern`)
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For questions or issues, please create an issue in the GitHub repository or contact the maintainers.

---
**Note:** This workspace is designed for educational purposes and interview preparation for Senior SDET roles. Each pattern is implemented with real-world scenarios and best practices.
