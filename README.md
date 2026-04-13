# 🦖 Fossil Finder North America

A modern Spring Boot REST API for discovering fossil hunting locations across North America. Search through 12,000+ documented fossil sites spanning 50 US states, 13 Canadian provinces, and the Bahamas.

## ✨ Features

- 🔍 **Smart Search**: Search by location name, fossil type, geological age, or formation
- 🗺️ **65 Territories**: Complete coverage of US states, Canadian provinces, DC, and Bahamas
- 📍 **12,000+ Locations**: Comprehensive database of fossil hunting sites
- 🚀 **Fast API**: Built with Spring Boot for high performance
- 📊 **Rich Data**: Includes directions, geological info, fossil types, and coordinates
- 🌐 **CORS Enabled**: Ready for frontend integration

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Run Locally

```bash
# Clone the repository
git clone https://github.com/yourusername/fossil-finder.git
cd fossil-finder

# Build the project
mvn clean package

# Run the application
java -jar target/fossil-finder-1.0.0.jar

# API is now running at http://localhost:8080
```

## 📡 API Endpoints

### Get All Territories
```http
GET /api/territories
```

Returns metadata for all 65 territories.

### Get Territory Locations
```http
GET /api/territories/{code}
```

Get all fossil locations for a specific territory (e.g., `OH` for Ohio).

### Search Locations
```http
GET /api/search?q=trilobite&territory=OH
```

Search for fossil locations by keyword across all fields.

**Query Parameters:**
- `q` - Search query (required)
- `territory` - Filter by territory code (optional)

### Health Check
```http
GET /api/health
```

## 📂 Project Structure
fossil-finder/
├── src/
│   ├── main/
│   │   ├── java/com/fossilfinder/
│   │   │   ├── FossilFinderApplication.java
│   │   │   ├── ScraperCommandLineRunner.java
│   │   │   ├── config/
│   │   │   │   └── WebConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── TerritoryController.java
│   │   │   │   └── HealthController.java
│   │   │   ├── service/
│   │   │   │   ├── FossilDataService.java
│   │   │   │   └── ScraperService.java
│   │   │   ├── model/
│   │   │   │   ├── FossilLocation.java
│   │   │   │   ├── Territory.java
│   │   │   │   └── SearchResult.java
│   │   │   └── exception/
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── fossil-finder.html
│   │       │   └── css/
│   │       └── data/
│   │           └── [65 territory JSON files]
│   └── test/java/
├── Dockerfile
├── pom.xml
└── README.md

## 🗄️ Data Structure

### Territory Metadata
```json
{
  "code": "OH",
  "name": "Ohio",
  "country": "United States",
  "dataFile": "OH.json",
  "locationCount": 304
}
```

### Fossil Location
```json
{
  "location": "Caesar Creek Lake",
  "county": "Warren County",
  "state": "Ohio",
  "directions": "Off Route 73",
  "age": "Ordovician",
  "formation": "Waynesville Formation",
  "fossils": "Brachiopods, trilobites, crinoids",
  "comments": "Excellent fossil collecting",
  "reference": "Field guide to Ohio fossils",
  "latLong": "39.5,-84.1"
}
```

## 🛠️ Development

### Run Tests
```bash
mvn test
```

### Run Web Scraper
```bash
# Scrape fresh data from fossilspot.com
java -jar target/fossil-finder-1.0.0.jar --scraper
```

### Build Docker Image
```bash
docker build -t fossil-finder .
docker run -p 8080:8080 fossil-finder
```

## 🌍 Covered Territories

**United States (50 states + DC)**
- All 50 states plus District of Columbia

**Canada (13 provinces/territories)**
- Alberta, British Columbia, Manitoba, New Brunswick, Newfoundland and Labrador
- Northwest Territories, Nova Scotia, Nunavut, Ontario, Prince Edward Island
- Quebec, Saskatchewan, Yukon

**Caribbean**
- Bahamas

## 📊 Statistics

- **Total Territories**: 65
- **Total Locations**: 12,000+
- **Most Locations**: New York (1,013), Oregon (660), California (588)
- **Data Source**: fossilspot.com
- **Last Updated**: April 2026

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is licensed under the MIT License.

## 🙏 Acknowledgments

- Data sourced from [fossilspot.com](http://www.fossilspot.com)
- Built with Spring Boot, Jackson, and JSoup

---

⭐ **Star this repo if you find it useful!**
