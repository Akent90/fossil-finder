#!/bin/bash
set -e

echo "======================================"
echo "  Building Fossil Finder (Spring Boot)"
echo "======================================"
echo ""

# Check Java
if ! command -v java &> /dev/null; then
    echo "❌ Java not installed"
    echo "   Install: brew install openjdk@17"
    exit 1
fi

# Check Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven not installed"
    echo "   Install: brew install maven"
    exit 1
fi

echo "✓ Java version: $(java -version 2>&1 | head -n 1)"
echo "✓ Maven version: $(mvn -version | head -n 1)"
echo ""

# Build
echo "Building with Maven..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
    echo "JAR created: target/fossil-finder-1.0.0.jar"
    echo ""
    echo "Next steps:"
    echo "  ./scripts/run-server.sh   - Start Spring Boot server"
    echo "  ./scripts/run-scraper.sh  - Scrape all territories (optional)"
    echo "  mvn test                  - Run unit tests"
    echo "  mvn spring-boot:run       - Run with Maven (dev mode)"
else
    echo ""
    echo "❌ Build failed"
    exit 1
fi