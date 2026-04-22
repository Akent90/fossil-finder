# 🦖 Fossil Finder North America

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Status](https://img.shields.io/badge/status-active_development-brightgreen)
![Java](https://img.shields.io/badge/Java-17+-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

A modern Spring Boot REST API for discovering fossil hunting locations across North America. Search through 12,000+ documented fossil sites spanning 50 US states, 13 Canadian provinces, and the Bahamas.

> **v1.0 — Initial Release.** This is a working proof-of-concept with a fully functional API and frontend. Active development is ongoing — see the [Roadmap](#-roadmap) for what's coming next.

## 🌐 Live Demo

**<a href="https://fossil-finder-717506296838.us-central1.run.app/index.html" target="_blank">https://fossil-finder-717506296838.us-central1.run.app/index.html</a>**

Deployed on Google Cloud Run.

## ✨ Features

- 🔍 **Smart Search**: Search by location name, fossil type, geological age, or formation
- 🗺️ **65 Territories**: Complete coverage of US states, Canadian provinces, DC, and Bahamas
- 📍 **12,000+ Locations**: Comprehensive database of fossil hunting sites
- 🚀 **REST API**: Built with Spring Boot following REST principles
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

## 🗺️ Roadmap

This is a v1.0 proof-of-concept. Planned features for upcoming releases include:

- [ ] **Interactive Map UI** — Visual map interface with pinned fossil site locations
- [ ] **User Accounts & Favorites** — Save and organize your favorite fossil sites
- [ ] **Location Reviews & Photos** — Community-submitted field notes and images
- [ ] **Advanced Filtering** — Filter by geological period, fossil type, difficulty, and more
- [ ] **Mobile App** — Native iOS/Android companion app
- [ ] **Expanded Dataset** — Additional international locations beyond North America
- [ ] **Custom Domain** — Clean production URL

## 📋 Data & Disclaimer

All location data in this application is sourced directly from [fossilspot.com](http://www.fossilspot.com) and is reproduced here as-is. The following notes apply to the dataset, as acknowledged by fossilspot.com themselves:

- **Data accuracy**: The original dataset contains known errors and ambiguities introduced across multiple transcriptions over the decades. It has been spell-checked and reviewed for plausibility, but inaccuracies remain.
- **Site availability**: Many listed sites are closed to collecting, buried under development, or no longer accessible. Closed sites are intentionally retained, as access can change and the same formations often appear nearby.
- **Legal collecting**: In the US, permission is generally required to collect fossil vertebrate material. Rules for invertebrates and plant material vary by land type. Always verify local regulations and obtain permission before collecting — especially on private property.
- **Coordinates**: Most latitude/longitude values are approximations derived from town or geographic feature lookups, not precise site coordinates. Treat them as general map markers only.
- **Formations**: Formation names are not always unique and may vary by region or paper. Some entries reference proposed names that were never widely adopted.

This project does not claim ownership of the underlying data. Full credit goes to fossilspot.com and its contributors, including Howard Allen of Calgary, Alberta.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is licensed under the MIT License.

## 🙏 Acknowledgments

- Data sourced from [fossilspot.com](http://www.fossilspot.com)
- Built with Spring Boot, Jackson, and JSoup

---

⭐ **Star this repo if you find it useful!**
