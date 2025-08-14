#!/bin/bash

echo "========================================"
echo "Design Patterns Test Automation Workspace"
echo "========================================"

echo ""
echo "Current Directory: $(pwd)"
echo "Java Version:"
java -version

echo ""
echo "Maven Version:"
mvn -version

echo ""
echo "========================================"
echo "Available Commands:"
echo "========================================"
echo ""
echo "1. mvn clean compile                    - Compile all projects"
echo "2. mvn clean test                       - Run all tests"
echo "3. mvn clean test -Dbrowser=chrome      - Run with Chrome browser"
echo "4. mvn clean test -Dbrowser=firefox     - Run with Firefox browser"
echo "5. mvn clean test -Dheadless=true       - Run in headless mode"
echo "6. mvn clean test -P remote             - Run on Selenium Grid"
echo "7. mvn allure:serve                     - Generate and serve Allure reports"
echo "8. docker-compose up                    - Start Selenium Grid with Docker"
echo "9. docker-compose down                  - Stop Docker containers"
echo ""

echo "========================================"
echo "Quick Start Commands:"
echo "========================================"
echo ""

read -p "Enter your choice (1-9) or 'q' to quit: " choice

case $choice in
    1)
        echo "Running: mvn clean compile"
        mvn clean compile
        ;;
    2)
        echo "Running: mvn clean test"
        mvn clean test
        ;;
    3)
        echo "Running: mvn clean test -Dbrowser=chrome"
        mvn clean test -Dbrowser=chrome
        ;;
    4)
        echo "Running: mvn clean test -Dbrowser=firefox"
        mvn clean test -Dbrowser=firefox
        ;;
    5)
        echo "Running: mvn clean test -Dheadless=true"
        mvn clean test -Dheadless=true
        ;;
    6)
        echo "Running: mvn clean test -P remote"
        mvn clean test -P remote
        ;;
    7)
        echo "Running: mvn allure:serve"
        mvn allure:serve
        ;;
    8)
        echo "Running: docker-compose up"
        docker-compose up -d
        ;;
    9)
        echo "Running: docker-compose down"
        docker-compose down
        ;;
    q)
        echo "Goodbye!"
        exit 0
        ;;
    *)
        echo "Invalid choice. Please try again."
        ;;
esac

echo ""
echo "Press Enter to continue..."
read
