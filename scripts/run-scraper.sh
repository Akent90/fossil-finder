#!/bin/bash
set -e

JAR_FILE="target/fossil-finder-1.0.0.jar"

if [ ! -f "$JAR_FILE" ]; then
    echo "❌ JAR not found. Run ./scripts/build.sh first"
    exit 1
fi

echo "======================================"
echo "  Fossil Data Scraper"
echo "======================================"
echo ""
echo "⚠️  WARNING: This will scrape fossilspot.com"
echo "    Scraping all 65 territories takes 5-10 minutes"
echo "    1-second delay between requests (respectful)"
echo ""
read -p "Continue? (y/N) " -n 1 -r
echo ""

if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Scraping cancelled"
    exit 0
fi

echo ""
echo "Starting scraper..."
echo ""

# Create data directory
mkdir -p data

# Run with scraper enabled
java -jar "$JAR_FILE" --scraper.enabled=true

echo ""
echo "✅ Scraping complete!"
echo ""
echo "Data saved to: data/"
echo "Files created: data/*.json"
echo ""
echo "To use scraped data in application:"
echo "  1. Copy data/*.json to src/main/resources/data/"
echo "  2. Rebuild: ./scripts/build.sh"
echo "  3. Run: ./scripts/run-server.sh"