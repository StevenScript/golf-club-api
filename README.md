# Golf Club API
## Steven Norris
## March 24th 2025

This project is a functional REST API for managing a golf club’s tournaments and memberships. It was built using Spring Boot, JPA, MySQL, and Lombok following a Test-Driven Development (TDD) approach with trunk-based commits and CI/CD using GitHub Actions. The project is also fully Dockerized so that any developer can clone the repository and get the API running quickly.

## Features

### Members
- **Fields**:
  - **ID** (auto-generated)
  - **Member Name**
  - **Member Address**
  - **Member Email Address**
  - **Member Phone Number**
  - **Start Date of Membership**
  - **Duration of Membership**
- **Operations**:
  - **Add a Member** (`POST /api/members`)
  - **Get a Member by ID** (`GET /api/members/{id}`)
  - **Get All Members** (`GET /api/members`)
  - **Search Members** (`GET /api/members/search`):
    - By **name** (`?name={name}`)
    - By **phone number** (`?phone={phone}`)
    - By **tournament start date** (`?tournamentStart={startDate}`) – returns all members who are participating in any tournament starting on the specified date.

### Tournaments
- **Fields**:
  - **ID** (auto-generated)
  - **Start Date**
  - **End Date**
  - **Location**
  - **Entry Fee**
  - **Cash Prize Amount**
  - **Participating Members** (Many-to-Many relationship with Members)
- **Operations**:
  - **Add a Tournament** (`POST /api/tournaments`)
  - **Get a Tournament by ID** (`GET /api/tournaments/{id}`)
  - **Get All Tournaments** (`GET /api/tournaments`)
  - **Search Tournaments** (`GET /api/tournaments/search`):
    - By **start date** (`?startDate={startDate}`)
    - By **location** (`?location={location}`)
  - **Assign a Member to a Tournament** (`POST /api/tournaments/{tournamentId}/members/{memberId}`)
  - **Get All Members in a Tournament** (`GET /api/tournaments/{id}/members`)

## Supported Search APIs

### For Members:
- **Search by Name**  
  `GET /api/members/search?name=John`
  
- **Search by Phone**  
  `GET /api/members/search?phone=555`
  
- **Search by Tournament Start Date**  
  `GET /api/members/search?tournamentStart=2023-10-01`  
  *Returns all members assigned to any tournament that starts on 2023-10-01.*

### For Tournaments:
- **Search by Start Date**  
  `GET /api/tournaments/search?startDate=2023-06-01`
  
- **Search by Location**  
  `GET /api/tournaments/search?location=pebble`
  
- **Retrieve All Members in a Tournament**  
  `GET /api/tournaments/{id}/members`

## Running the Project in Docker

This project is Dockerized so that you can quickly spin up your development environment (both the API and MySQL) without needing to install MySQL locally.

### Prerequisites
- [Docker Desktop](https://www.docker.com/products/docker-desktop) (or another Docker Engine installation)
- Docker Compose (usually bundled with Docker Desktop)

### Files Included
- **Dockerfile**: Builds the Spring Boot application into a runnable Docker image.
- **docker-compose.yml**: Defines two services:
  - **db**: A MySQL container.
  - **golf-club-api**: Your Spring Boot application container.

### How to Run with Docker Compose

1. Open a terminal in the project’s root directory (where both `Dockerfile` and `docker-compose.yml` are located).
2. Build and start the containers with:
   ```bash
   docker-compose up --build
   ```
3. Once the containers are running:
   - The **MySQL** container will be running with the following credentials:
     - **Database**: `golf_db`
     - **User**: `golfuser`
     - **Password**: `password`
   - The **golf-club-api** container will be available at:
     ```
     http://localhost:8080
     ```
4. To stop the containers, press **Ctrl+C** in the terminal or run:
   ```bash
   docker-compose down
   ```

---

## Testing with Postman

After your containers are running, use Postman to verify your API endpoints:

1. **Add a Member**  
   - **Method**: `POST`
   - **URL**: `http://localhost:8080/api/members`
   - **Body (JSON)**:
     ```json
     {
       "memberName": "John Doe",
       "address": "123 Golf St",
       "emailAddress": "john@example.com",
       "phoneNumber": "555-1234",
       "startDateOfMembership": "2023-01-01",
       "durationOfMembership": "1 year"
     }
     ```

2. **Get a Member**  
   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/members/{id}`

3. **Add a Tournament**  
   - **Method**: `POST`
   - **URL**: `http://localhost:8080/api/tournaments`
   - **Body (JSON)**:
     ```json
     {
       "startDate": "2023-06-01",
       "endDate": "2023-06-05",
       "location": "Pebble Beach",
       "entryFee": 200,
       "cashPrizeAmount": 5000
     }
     ```

4. **Assign Member to Tournament**  
   - **Method**: `POST`
   - **URL**: `http://localhost:8080/api/tournaments/{tournamentId}/members/{memberId}`

5. **Search Members by Tournament Start Date**  
   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/members/search?tournamentStart=2023-06-01`

6. **Search Tournaments by Start Date or Location**  
   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/tournaments/search?startDate=2023-06-01`  
     or  
     `http://localhost:8080/api/tournaments/search?location=pebble`

7. **Get All Members in a Tournament**  
   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/tournaments/{id}/members`

For each endpoint, take a screenshot of:
- The Postman **request** (URL, method, body if applicable)
- The **response** (status code and JSON payload)

Include these screenshots in your final submission.

---

## Running Tests Locally

To run the tests locally, use Maven:
```bash
mvn clean test
```
Your CI pipeline in GitHub Actions will also run these tests on every commit.

---

## Conclusion

This project provides:
- A fully functional REST API for managing golf club members and tournaments.
- Search capabilities for both members and tournaments.
- A many-to-many relationship between members and tournaments.
- Docker support so the API and MySQL database can be run seamlessly by any developer.
- Comprehensive Postman tests to verify API functionality.

Follow these instructions to run the API locally or in Docker, and use Postman to validate all endpoints.

---
