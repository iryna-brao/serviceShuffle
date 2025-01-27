
# serviceLogForCommitOffshore

# QA Automation Test: End-to-End Microservices Implementation

This project demonstrates an end-to-end system with two microservices: `service-shuffle` for processing requests and `service-log` for logging them. Below are the detailed instructions for setting up, running, and testing the application.

## **System Overview**
- **service-shuffle**:
    - Accepts a number (1 to 1000) via a POST API.
    - Returns a shuffled array of numbers from 1 to the given input.
    - Sends an asynchronous log request to `service-log`.

- **service-log**:
    - Logs the request message received from `service-shuffle` to the console.

### Architecture Diagram
Below is a high-level view of the interaction between the two services:

```
Client --> [POST /shuffle] --> Service-Shuffle --> [POST /log] --> Service-Log
                     ^                                           |
                     |-------------------------------------------|
                               Returns Shuffled Array
```

## **Technologies Used**
- **Spring Boot**: Backend framework for microservices.
- **Java 11**: Programming language.
- **Maven**: Build and dependency management.
- **RestTemplate**: HTTP client for making requests between services.
- **SLF4J**: Logging framework.

---

## **Setup Instructions**

### **Prerequisites**
1. **Java 11** or later installed.
2. **Maven** installed.
3. Any IDE (e.g., IntelliJ IDEA, Eclipse) or terminal.

### **Steps to Run the Project**

#### **1. Clone the repository**
```bash
git clone <repository_url>
cd <repository_folder>
```

#### **2. Configure `application.properties`**
In `service-shuffle`:
- Set the URL for `service-log` in `application.properties`:
```properties
log.service.url=http://localhost:8081
```

#### **3. Start `service-log`**
Navigate to the `service-log` folder and run the following command:
```bash
mvn spring-boot:run
```
The `service-log` application will start on port `8081`.

#### **4. Start `service-shuffle`**
Navigate to the `service-shuffle` folder and run:
```bash
mvn spring-boot:run
```
The `service-shuffle` application will start on port `8080`.

---

## **Using the Application**

### **API Endpoints**

#### **1. Shuffle Numbers**
**POST** `/api/v1/shuffle`
- Request Body: A number between 1 and 1000.
```json
{
  "number": 5
}
```
- Response: A shuffled array.
```json
[4, 2, 1, 5, 3]
```

#### **2. Log Request**
Logs are automatically sent from `service-shuffle` to `service-log` and printed in the console.

---

## **Testing Instructions**

### **Run Unit Tests**
1. Navigate to the root folder of each service (`service-shuffle` or `service-log`).
2. Execute the following command:
```bash
mvn test
```
3. Verify that all tests pass successfully.

### **Sample Test Cases**
- Valid numbers (e.g., 5, 100) return correctly shuffled arrays.
- Invalid inputs (e.g., -1, 1500) result in a `400 Bad Request`.
- Asynchronous logging is successfully sent to `service-log`.

---

## **Expected Outputs**

### **Example Request to `service-shuffle`**
```bash
curl -X POST http://localhost:8080/api/v1/shuffle \
     -H 'Content-Type: application/json' \
     -d '5'
```
Response:
```json
[3, 5, 1, 2, 4]
```

### **Log Output in `service-log`**
```
INFO: Received log message: Shuffled array for number: 5
```

---
## **Contact**
For any questions or issues, please contact iryna.boblieva@gmail.com.

