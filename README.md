# fossil-finder

North American fossil location finder with Spring Boot REST API and modern web interface

# 🦴 Fossil Finder - North America

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue.svg)](https://maven.apache.org)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A comprehensive Spring Boot web application for discovering paleontological sites across 65 territories in North America.

## 🌟 Features

- **65 Territories**: All 50 US states, 13 Canadian provinces/territories, DC, and Bahamas
- **Spring Boot REST API**: Production-ready with health checks and metrics
- **Advanced Search**: Filter by location, geological age, formation, and fossil type
- **Beautiful UI**: Responsive design with geological theme
- **Auto Scraper**: Fetch data from fossilspot.com
- **API Documentation**: Interactive Swagger UI
- **Cloud Ready**: Docker support and GCP Cloud Run compatible

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.9 or higher
- Modern web browser

### Installation

```bash
# Clone repository
git clone https://github.com/YOUR_USERNAME/fossil-finder.git
cd fossil-finder

# Build
./scripts/build.sh

# Run server
./scripts/run-server.sh

# Open browser
open http://localhost:8080
```

## 📁 Project Structure

fossil-finder/
├── src/main/java/com/fossilfinder/
│ ├── FossilFinderApplication.java # Main Spring Boot app
│ ├── config/
│ │ └── WebConfig.java # CORS configuration
│ ├── controller/
│ │ ├── TerritoryController.java # REST endpoints
│ │ └── HealthController.java # Health check
│ ├── service/
│ │ ├── FossilDataService.java # Business logic
│ │ └── ScraperService.java # Web scraper
│ ├── model/
│ │ ├── FossilLocation.java # Data models
│ │ ├── Territory.java
│ │ └── SearchResult.java
│ └── exception/
│ └── GlobalExceptionHandler.java # Error handling
├── src/main/resources/
│ ├── application.yml # Configuration
│ ├── static/
│ │ ├── css/ # Stylesheets
│ │ ├── index.html # Landing page
│ │ └── fossil-finder.html # Search page
│ └── data/ # JSON data
├── src/test/java/ # Unit tests
├── scripts/ # Build scripts
├── Dockerfile # Docker config
└── pom.xml # Maven config

## 🔧 API Endpoints

GET /health - Health check
GET /api/territories - List all territories
GET /api/territory/{code} - Get territory data
GET /api/search?q={term} - Search fossils
GET /swagger-ui.html - API Documentation
GET /actuator/health - Actuator health

## 🛠️ Development

### Run with Maven

```bash
# Development mode (hot reload)
mvn spring-boot:run

# Run tests
mvn test

# Package
mvn clean package
```

### Run Scraper

```bash
# Scrape all 65 territories (takes 5-10 minutes)
./scripts/run-scraper.sh

# Or with Maven
mvn spring-boot:run -Dspring-boot.run.arguments=--scraper.enabled=true
```

### Docker

```bash
# Build image
docker build -t fossil-finder:latest .

# Run container
docker run -p 8080:8080 fossil-finder:latest

# Test
curl http://localhost:8080/health
```

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=TerritoryControllerTest

# With coverage
mvn test jacoco:report
```

## 📊 Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Web**: Spring Boot Web (REST), Jackson (JSON)
- **Monitoring**: Spring Boot Actuator
- **Documentation**: Springdoc OpenAPI (Swagger)
- **Testing**: JUnit 5, MockMvc, Mockito
- **Build**: Maven 3.9
- **Scraping**: JSoup 1.17.2
- **Utilities**: Lombok
- **Frontend**: Vanilla JavaScript, CSS3, HTML5

## 🌐 Data Source

Data scraped from [fossilspot.com](http://www.fossilspot.com) - a comprehensive fossil location database.

## 🤝 Contributing

Contributions welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file.

## ⚖️ Legal & Ethics

- For educational and research purposes
- Always get permission before collecting on private land
- Follow local laws and regulations
- Respect protected areas

## 🙏 Acknowledgments

- Data from [fossilspot.com](http://www.fossilspot.com)
- Built with Spring Boot, Maven, and modern web technologies
- Geological design inspired by sedimentary layers

---

**Happy Fossil Hunting! 🦕**
