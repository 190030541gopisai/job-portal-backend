# 🚀 Job Portal System

Enterprise-grade microservices-based job portal with AI-powered resume parsing and job matching capabilities.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://www.oracle.com/java/)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.3-blue.svg)](https://spring.io/projects/spring-cloud)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue.svg)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)

## 📋 Table of Contents

- [Overview](#-overview)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Microservices](#-microservices)
- [Features](#-features)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [Deployment](#-deployment)
- [Contributing](#-contributing)
- [License](#-license)

## 🎯 Overview

A production-ready job portal system built with microservices architecture, featuring AI-powered capabilities for intelligent resume parsing and job matching. The system handles user management, company profiles, job postings, applications, and personalized recommendations through distributed services.

**Key Highlights:**
- 🏗️ **Microservices Architecture** - 8 independent services with dedicated databases
- 🤖 **AI Integration** - Google Gemini AI for resume parsing and job matching
- 🔐 **Security** - JWT-based authentication and authorization
- 🌐 **Service Discovery** - Netflix Eureka for service registration
- ⚙️ **Centralized Config** - Spring Cloud Config Server
- 🚪 **API Gateway** - Single entry point with routing and load balancing
- 🐳 **Containerized** - Full Docker Compose orchestration
- 📊 **Database per Service** - PostgreSQL with isolated schemas

## 🏛️ Architecture

```
                                    ┌──────────────────┐
                                    │   API Gateway    │
                                    │   (Port: 5000)   │
                                    └────────┬─────────┘
                                             │
                    ┌────────────────────────┼────────────────────────┐
                    │                        │                        │
         ┌──────────▼──────────┐  ┌─────────▼─────────┐  ┌──────────▼──────────┐
         │  Service Registry   │  │   Config Server   │  │    PostgreSQL       │
         │   (Eureka: 8761)    │  │   (Port: 8888)    │  │   (Port: 5432)      │
         └─────────────────────┘  └───────────────────┘  └─────────────────────┘
                    │
    ┌───────────────┼───────────────┬───────────────┬───────────────┐
    │               │               │               │               │
┌───▼────┐    ┌────▼─────┐   ┌────▼─────┐   ┌─────▼────┐   ┌─────▼────┐
│  User  │    │ Company  │   │   Job    │   │  Resume  │   │   App    │
│ (5001) │    │  (5002)  │   │  (5003)  │   │  (5004)  │   │  (5005)  │
└────────┘    └──────────┘   └──────────┘   └──────────┘   └──────────┘
    │               │               │               │               │
┌───▼────┐    ┌────▼─────┐   ┌────▼─────┐
│  Pref  │    │  Notify  │   │    AI    │
│ (5006) │    │  (5007)  │   │  (6000)  │
└────────┘    └──────────┘   └──────────┘
```

### Architecture Patterns Implemented

- **API Gateway Pattern** - Single entry point for all client requests
- **Service Registry Pattern** - Dynamic service discovery with Eureka
- **Database per Service** - Each microservice owns its database
- **Externalized Configuration** - Centralized config management
- **Circuit Breaker** - Resilience with Spring Cloud Circuit Breaker
- **Distributed Tracing** - Request tracking across services

## 🛠️ Tech Stack

### Backend Framework
- **Spring Boot 4.1.1** - Application framework
- **Spring Cloud 2025.1.3** - Microservices infrastructure
- **Java 25** - Programming language

### Cloud Services
- **Spring Cloud Netflix Eureka** - Service registry and discovery
- **Spring Cloud Config** - Externalized configuration
- **Spring Cloud Gateway** - API gateway and routing

### Data & Persistence
- **PostgreSQL 16** - Relational database
- **Spring Data JPA** - ORM and data access
- **Hibernate** - JPA implementation

### Security
- **Spring Security** - Authentication and authorization
- **JWT (jjwt 0.13.0)** - Token-based authentication

### AI/ML
- **Google Gemini AI** - Resume parsing and job matching
- **Spring AI** - AI integration framework

### DevOps & Tools
- **Docker & Docker Compose** - Containerization
- **Jib Maven Plugin** - Container image building
- **Maven** - Dependency management and build
- **Lombok** - Boilerplate code reduction

## 📦 Microservices

### Cloud Infrastructure Services

#### 1. **Service Registry** (Port: 8761)
- Netflix Eureka server for service discovery
- Health monitoring and load balancing
- Dashboard: `http://localhost:8761`

#### 2. **Config Server** (Port: 8888)
- Centralized configuration management
- Environment-specific configurations
- Git-backed configuration repository

#### 3. **API Gateway** (Port: 5000)
- Single entry point for all APIs
- Request routing and load balancing
- Authentication and rate limiting
- API: `http://localhost:5000`

### Business Services

#### 4. **User Service** (Port: 5001)
**Responsibilities:**
- User registration and authentication
- Profile management (job seekers & employers)
- JWT token generation and validation
- Role-based access control

**Key Endpoints:**
```
POST   /api/users/register          - User registration
POST   /api/users/login              - User authentication
GET    /api/users/profile/{id}       - Get user profile
PUT    /api/users/profile/{id}       - Update profile
DELETE /api/users/{id}               - Delete user
```

**Database:** `job_portal_user`

#### 5. **Company Service** (Port: 5002)
**Responsibilities:**
- Company profile management
- Company verification
- Industry and size categorization
- Company search and filtering

**Key Endpoints:**
```
POST   /api/companies                - Create company
GET    /api/companies/{id}           - Get company details
GET    /api/companies                - List companies
PUT    /api/companies/{id}           - Update company
DELETE /api/companies/{id}           - Delete company
GET    /api/companies/search         - Search companies
```

**Database:** `job_portal_company`

#### 6. **Job Service** (Port: 5003)
**Responsibilities:**
- Job posting creation and management
- Job search with filters
- Skill and category management
- Job status tracking

**Key Endpoints:**
```
POST   /api/jobs                     - Create job posting
GET    /api/jobs/{id}                - Get job details
GET    /api/jobs                     - List all jobs
GET    /api/jobs/search              - Search jobs
PUT    /api/jobs/{id}                - Update job
DELETE /api/jobs/{id}                - Delete job
POST   /api/jobs/{id}/skills         - Add required skills
GET    /api/categories               - Get job categories
```

**Database:** `job_portal_job`

#### 7. **Resume Service** (Port: 5004)
**Responsibilities:**
- Resume upload and storage
- AI-powered resume parsing
- Skills extraction
- Resume versioning

**Key Endpoints:**
```
POST   /api/resumes                  - Upload resume
GET    /api/resumes/user/{userId}    - Get user resumes
GET    /api/resumes/{id}             - Get resume details
POST   /api/resumes/parse            - Parse resume with AI
PUT    /api/resumes/{id}             - Update resume
DELETE /api/resumes/{id}             - Delete resume
```

**Database:** `job_portal_resume`

#### 8. **Application Service** (Port: 5005)
**Responsibilities:**
- Job application submission
- Application status tracking
- Application history
- Employer application management

**Key Endpoints:**
```
POST   /api/applications             - Submit application
GET    /api/applications/{id}        - Get application details
GET    /api/applications/user/{userId} - User's applications
GET    /api/applications/job/{jobId} - Job applications
PUT    /api/applications/{id}/status - Update status
DELETE /api/applications/{id}        - Withdraw application
```

**Database:** `job_portal_application`

#### 9. **Preferences Service** (Port: 5006)
**Responsibilities:**
- User job preferences
- Notification preferences
- Search preferences
- Recommendation settings

**Key Endpoints:**
```
POST   /api/preferences              - Set preferences
GET    /api/preferences/user/{userId} - Get user preferences
PUT    /api/preferences/{id}         - Update preferences
DELETE /api/preferences/{id}         - Delete preferences
```

**Database:** `job_portal_preferences`

#### 10. **Notification Service** (Port: 5007)
**Responsibilities:**
- Email notifications
- In-app notifications
- Job alerts
- Application status updates

**Key Endpoints:**
```
POST   /api/notifications            - Create notification
GET    /api/notifications/user/{userId} - User notifications
PUT    /api/notifications/{id}/read  - Mark as read
DELETE /api/notifications/{id}       - Delete notification
```

**Database:** `job_portal_notification` (via common lib)

#### 11. **AI Service** (Port: 6000)
**Responsibilities:**
- Resume parsing with Google Gemini AI
- Job-resume matching
- Skill extraction
- Job recommendations

**Key Endpoints:**
```
POST   /api/ai/parse-resume          - Parse resume document
POST   /api/ai/match-jobs            - Match resume to jobs
POST   /api/ai/extract-skills        - Extract skills from text
POST   /api/ai/recommend-jobs        - Get job recommendations
```

**Integration:** Google Gemini AI API

## ✨ Features

### For Job Seekers
- ✅ User registration and profile management
- ✅ AI-powered resume parsing and analysis
- ✅ Advanced job search with filters
- ✅ One-click job applications
- ✅ Application status tracking
- ✅ Personalized job recommendations
- ✅ Email and in-app notifications
- ✅ Job preference settings

### For Employers
- ✅ Company profile creation
- ✅ Job posting management
- ✅ Application tracking
- ✅ Candidate search
- ✅ Application status updates
- ✅ Skills-based filtering

### Technical Features
- ✅ JWT-based authentication
- ✅ Role-based access control (RBAC)
- ✅ RESTful API design
- ✅ Service-to-service communication
- ✅ Centralized logging
- ✅ Health checks and monitoring
- ✅ Docker containerization
- ✅ Database migration support

## 📋 Prerequisites

Ensure you have the following installed:

- **Java 25** ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.9+** ([Download](https://maven.apache.org/download.cgi))
- **Docker & Docker Compose** ([Download](https://www.docker.com/products/docker-desktop))
- **Git** ([Download](https://git-scm.com/downloads))
- **PostgreSQL 16** (Optional - for local development)
- **Google Gemini API Key** ([Get API Key](https://makersuite.google.com/app/apikey))

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd job-portal-system
```

### 2. Environment Configuration

Create `.env` file in project root:

```bash
cp .env.example .env
```

Edit `.env` with your credentials:

```env
# Database Configuration
DB_USERNAME=postgres
DB_PASSWORD=your_secure_password

# AI Service Configuration
GOOGLE_API_KEY=your_gemini_api_key
```

### 3. Build the Project

```bash
# Build all modules
mvn clean install

# Skip tests (faster build)
mvn clean install -DskipTests
```

### 4. Build Docker Images

```bash
# Make script executable
chmod +x build-images.sh

# Build all service images
./build-images.sh
```

This creates Docker images for:
- `gopi/job-portal-service-registry:latest`
- `gopi/job-portal-config-server:latest`
- `gopi/job-portal-api-gateway:latest`
- `gopi/job-portal-user-service:latest`
- `gopi/job-portal-company-service:latest`
- `gopi/job-portal-job-service:latest`
- `gopi/job-portal-resume-service:latest`
- `gopi/job-portal-application-service:latest`
- `gopi/job-portal-preferences-service:latest`
- `gopi/job-portal-ai-service:latest`

### 5. Start the System

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Check service status
docker-compose ps
```

### 6. Service Startup Order

Services start in this sequence (via health checks):

1. **PostgreSQL** - Database initialization
2. **Service Registry** - Eureka server (wait for health)
3. **Config Server** - Configuration service (wait for health)
4. **API Gateway** - Entry point (depends on config)
5. **Business Services** - All microservices in parallel

**Total startup time:** ~2-3 minutes

### 7. Verify Deployment

Check these URLs:

| Service | URL | Status |
|---------|-----|--------|
| API Gateway | http://localhost:5000 | ✅ Main entry point |
| Eureka Dashboard | http://localhost:8761 | 📊 Service registry |
| Config Server | http://localhost:8888 | ⚙️ Configuration |
| User Service | http://localhost:5001/actuator/health | 💚 Health |
| Company Service | http://localhost:5002/actuator/health | 💚 Health |
| Job Service | http://localhost:5003/actuator/health | 💚 Health |

### 8. Stop the System

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (clean slate)
docker-compose down -v
```

## 📚 API Documentation

### Postman Collection

Import the Postman collection for complete API documentation:

```
Job Portal System.postman_collection.json
```

**Collection includes:**
- Pre-configured requests for all services
- Environment variables
- Authentication flows
- Sample payloads

### API Gateway Routes

All microservices are accessible via API Gateway at `http://localhost:5000`:

```
http://localhost:5000/api/users/**          → User Service
http://localhost:5000/api/companies/**      → Company Service
http://localhost:5000/api/jobs/**           → Job Service
http://localhost:5000/api/resumes/**        → Resume Service
http://localhost:5000/api/applications/**   → Application Service
http://localhost:5000/api/preferences/**    → Preferences Service
http://localhost:5000/api/notifications/** → Notification Service
http://localhost:5000/api/ai/**            → AI Service
```

### Authentication Flow

1. **Register User**
```bash
POST http://localhost:5000/api/users/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePass123",
  "fullName": "John Doe",
  "role": "JOB_SEEKER"
}
```

2. **Login**
```bash
POST http://localhost:5000/api/users/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePass123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "role": "JOB_SEEKER"
}
```

3. **Access Protected Endpoints**
```bash
GET http://localhost:5000/api/users/profile/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Sample API Calls

#### Create Company
```bash
POST http://localhost:5000/api/companies
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Tech Corp",
  "description": "Leading technology company",
  "industry": "Information Technology",
  "website": "https://techcorp.com",
  "size": "1000-5000"
}
```

#### Post a Job
```bash
POST http://localhost:5000/api/jobs
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Senior Java Developer",
  "description": "Looking for experienced Java developer...",
  "companyId": 1,
  "location": "New York, NY",
  "employmentType": "FULL_TIME",
  "experienceLevel": "SENIOR",
  "salaryMin": 120000,
  "salaryMax": 160000
}
```

#### Upload and Parse Resume
```bash
POST http://localhost:5000/api/resumes/parse
Authorization: Bearer <token>
Content-Type: multipart/form-data

file: resume.pdf
userId: 1
```

## 📁 Project Structure

```
job-portal-system/
├── cloud/                              # Cloud infrastructure services
│   ├── job-portal-service-registry/   # Eureka server
│   ├── job-portal-config-server/      # Config server
│   └── job-portal-api-gateway/        # API gateway
│
├── services/                           # Business microservices
│   ├── job-portal-user-service/       # User management
│   ├── job-portal-company-service/    # Company profiles
│   ├── job-portal-job-service/        # Job postings
│   ├── job-portal-resume-service/     # Resume handling
│   ├── job-portal-application-service/ # Job applications
│   ├── job-portal-preferences-service/ # User preferences
│   ├── job-portal-notification-service/ # Notifications
│   └── job-portal-ai-service/         # AI integration
│
├── common-lib/                         # Shared libraries
│   ├── dto/                           # Data transfer objects
│   ├── entity/                        # Common entities
│   ├── exception/                     # Exception handling
│   ├── security/                      # Security utilities
│   └── util/                          # Utility classes
│
├── docker-compose.yml                  # Docker orchestration
├── build-images.sh                     # Image build script
├── init-db.sql                        # Database initialization
├── .env.example                       # Environment template
├── Job Portal System.postman_collection.json
└── pom.xml                            # Parent POM
```

### Individual Service Structure

```
service-name/
├── src/
│   ├── main/
│   │   ├── java/com/gopi/job/service/
│   │   │   ├── controller/        # REST controllers
│   │   │   ├── service/           # Business logic
│   │   │   ├── repository/        # Data access
│   │   │   ├── entity/            # JPA entities
│   │   │   ├── dto/               # DTOs
│   │   │   ├── mapper/            # Entity-DTO mappers
│   │   │   ├── config/            # Configuration
│   │   │   └── Application.java   # Main class
│   │   └── resources/
│   │       ├── application.yml    # Service config
│   │       └── bootstrap.yml      # Bootstrap config
│   └── test/                      # Unit & integration tests
├── Dockerfile                      # Container definition
└── pom.xml                        # Maven configuration
```

## ⚙️ Configuration

### Application Properties

Each service has configuration in `application.yml`:

```yaml
server:
  port: 5001

spring:
  application:
    name: user-service
  datasource:
    url: jdbc:postgresql://localhost:5432/job_portal_user
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

### Environment Variables

| Variable | Description | Required |
|----------|-------------|----------|
| `DB_USERNAME` | PostgreSQL username | Yes |
| `DB_PASSWORD` | PostgreSQL password | Yes |
| `GOOGLE_API_KEY` | Google Gemini API key | Yes (AI Service) |
| `JWT_SECRET` | JWT signing key | Auto-generated |
| `JWT_EXPIRATION` | Token expiration (ms) | Optional |

### Database Configuration

Databases are auto-created via `init-db.sql`:

```sql
CREATE DATABASE job_portal_user;
CREATE DATABASE job_portal_company;
CREATE DATABASE job_portal_job;
CREATE DATABASE job_portal_resume;
CREATE DATABASE job_portal_application;
CREATE DATABASE job_portal_preferences;
```

Each service handles its own schema via JPA DDL auto-generation.

## 🚢 Deployment

### Local Development

```bash
# Run individual service
cd services/job-portal-user-service
mvn spring-boot:run

# With profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Docker Deployment

```bash
# Production deployment
docker-compose -f docker-compose.yml up -d

# Development with hot-reload
docker-compose -f docker-compose.dev.yml up
```

### Cloud Deployment

For cloud platforms (AWS, Azure, GCP):

1. **Container Registry**
   ```bash
   # Tag images
   docker tag gopi/job-portal-user-service:latest registry.example.com/user-service:v1.0
   
   # Push to registry
   docker push registry.example.com/user-service:v1.0
   ```

2. **Kubernetes Deployment**
   - Create K8s manifests for each service
   - Configure ConfigMaps and Secrets
   - Set up Ingress for API Gateway
   - Configure persistent volumes for PostgreSQL

3. **Environment-Specific Configs**
   - Use Config Server with Git backend
   - Separate profiles: `dev`, `staging`, `production`
   - Externalize sensitive data to secret managers

## 🔧 Development

### Running Tests

```bash
# Run all tests
mvn test

# Run tests for specific service
cd services/job-portal-user-service
mvn test

# Integration tests
mvn verify
```

### Code Quality

```bash
# Format code
mvn fmt:format

# Static analysis
mvn checkstyle:check

# Generate reports
mvn site
```

### Adding a New Service

1. Create service module under `services/`
2. Add dependency in parent `pom.xml`
3. Configure database in `init-db.sql`
4. Create Dockerfile
5. Add service to `docker-compose.yml`
6. Register with Eureka
7. Add routes in API Gateway

## 🐛 Troubleshooting

### Services Not Starting

```bash
# Check logs
docker-compose logs service-name

# Restart specific service
docker-compose restart service-name

# Rebuild and restart
docker-compose up -d --build service-name
```

### Database Connection Issues

```bash
# Verify PostgreSQL is running
docker-compose ps postgres

# Check database logs
docker-compose logs postgres

# Connect to database
docker exec -it job-portal-postgres psql -U postgres
```

### Service Registration Issues

```bash
# Check Eureka dashboard
open http://localhost:8761

# Verify service config
docker-compose exec user-service env | grep EUREKA
```

### Port Conflicts

```bash
# Check port usage
lsof -i :5000

# Kill process on port
kill -9 $(lsof -t -i:5000)

# Change port in docker-compose.yml
```

## 📊 Monitoring & Observability

### Health Checks

All services expose Spring Boot Actuator endpoints:

```bash
# Service health
curl http://localhost:5001/actuator/health

# Service info
curl http://localhost:5001/actuator/info

# Metrics
curl http://localhost:5001/actuator/metrics
```

### Service Discovery

Monitor registered services:

```bash
# Eureka dashboard
open http://localhost:8761
```

### Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f user-service

# Last 100 lines
docker-compose logs --tail=100 user-service
```

## 🔒 Security

### Authentication

- JWT-based token authentication
- Token expiration: 24 hours (configurable)
- Refresh token support
- Secure password hashing (BCrypt)

### Authorization

Role-based access control:
- `JOB_SEEKER` - Can search jobs, apply, manage profile
- `EMPLOYER` - Can post jobs, manage company, view applications
- `ADMIN` - Full system access

### API Security

- HTTPS in production (configure reverse proxy)
- CORS configuration
- Rate limiting on API Gateway
- Input validation and sanitization
- SQL injection prevention (JPA)

## 🤝 Contributing

Contributions are welcome! Follow these steps:

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

### Coding Standards

- Follow Java naming conventions
- Write unit tests for new features
- Update API documentation
- Add comments for complex logic
- Use Lombok for boilerplate reduction

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Gottipati Gopi Sai**

- GitHub: [@your-github](https://github.com/your-username)
- LinkedIn: [Your LinkedIn](https://linkedin.com/in/your-profile)
- Email: your.email@example.com

## 🙏 Acknowledgments

- Spring Boot & Spring Cloud teams
- Google Gemini AI
- Netflix OSS
- Docker & PostgreSQL communities

## 📞 Support

For questions or issues:

1. Check existing [Issues](../../issues)
2. Create new issue with detailed description
3. Include logs and environment details
4. Tag with appropriate labels

---

⭐ **If you find this project useful, please give it a star!** ⭐

Made with ❤️ using Spring Boot and Microservices Architecture
