#!/bin/bash
set -e

JAR_FILE="target/fossil-finder-1.0.0.jar"

if [ ! -f "$JAR_FILE" ]; then
    echo "❌ JAR not found. Run ./scripts/build.sh first"
    exit 1
fi

echo "======================================"
echo "  Fossil Finder API Server"
echo "======================================"
echo ""
echo "Starting Spring Boot application..."
echo ""
echo "Available URLs:"
echo "  http://localhost:8080              - Web UI"
echo "  http://localhost:8080/health       - Health check"
echo "  http://localhost:8080/api/territories - API endpoint"
echo "  http://localhost:8080/swagger-ui.html - API Documentation"
echo ""
echo "Press Ctrl+C to stop"
echo ""

java -jar "$JAR_FILE"