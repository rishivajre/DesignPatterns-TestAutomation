@echo off
echo ========================================
echo Design Patterns Test Automation Workspace
echo ========================================

echo.
echo Current Directory: %CD%
echo Java Version:
java -version

echo.
echo Maven Version:
mvn -version

echo.
echo ========================================
echo Available Commands:
echo ========================================
echo.
echo 1. mvn clean compile                    - Compile all projects
echo 2. mvn clean test                       - Run all tests
echo 3. mvn clean test -Dbrowser=chrome      - Run with Chrome browser
echo 4. mvn clean test -Dbrowser=firefox     - Run with Firefox browser
echo 5. mvn clean test -Dheadless=true       - Run in headless mode
echo 6. mvn clean test -P remote             - Run on Selenium Grid
echo 7. mvn allure:serve                     - Generate and serve Allure reports
echo 8. docker-compose up                    - Start Selenium Grid with Docker
echo 9. docker-compose down                  - Stop Docker containers
echo.

echo ========================================
echo Quick Start Commands:
echo ========================================
echo.

set /p choice="Enter your choice (1-9) or 'q' to quit: "

if "%choice%"=="1" (
    echo Running: mvn clean compile
    mvn clean compile
) else if "%choice%"=="2" (
    echo Running: mvn clean test
    mvn clean test
) else if "%choice%"=="3" (
    echo Running: mvn clean test -Dbrowser=chrome
    mvn clean test -Dbrowser=chrome
) else if "%choice%"=="4" (
    echo Running: mvn clean test -Dbrowser=firefox
    mvn clean test -Dbrowser=firefox
) else if "%choice%"=="5" (
    echo Running: mvn clean test -Dheadless=true
    mvn clean test -Dheadless=true
) else if "%choice%"=="6" (
    echo Running: mvn clean test -P remote
    mvn clean test -P remote
) else if "%choice%"=="7" (
    echo Running: mvn allure:serve
    mvn allure:serve
) else if "%choice%"=="8" (
    echo Running: docker-compose up
    docker-compose up -d
) else if "%choice%"=="9" (
    echo Running: docker-compose down
    docker-compose down
) else if "%choice%"=="q" (
    echo Goodbye!
    exit /b 0
) else (
    echo Invalid choice. Please try again.
)

echo.
echo Press any key to continue...
pause >nul
