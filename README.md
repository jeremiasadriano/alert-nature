# AlertNature
AlertNature is an application for natural disaster notifications. It is designed to provide quick and accurate alerts about events such as earthquakes, hurricanes, and other natural disasters.

## Features:

- Real-time notifications: Receive immediate alerts about natural disasters directly on your mobile device and email.
- Alerts based on intensity levels: Alerts are categorized into three intensity levels:
  - FIRST_LEVEL: Low-intensity alerts for minor disasters.
  - SECOND_LEVEL: Medium-intensity alerts for more significant disasters.
  - THIRD_LEVEL: High-intensity alerts for major disasters.

## Getting Started
To get started with AlertNature, follow these steps:
1. **Account Creation**: Create your account on our platform.
2. **OTP Verification**: After registering, you will receive a confirmation code (OTP) via SMS. Enter this code in the application to validate your account.
3. **Email Link Confirmation**: You can also receive a confirmation link in the email provided during registration. Click on the link to complete your account validation. 
4. **Alert Configuration**: You can create alerts for a province or city, see how many alerts exist, view active or inactive alerts, and activate or deactivate alerts as needed.
5. **For other feature you can see the api doc(swagger)**.
## Requirements
Before you start, ensure you have the following installed:
- IntelliJ IDEA 2024.1 or higher
- Maven (optional, if not using IntelliJ)
- Java 17 or higher
- PostgreSQL (if not using Docker)
- Docker (optional, if using Docker)
- Docker Compose (optional, if using Docker Compose)
- A [Twilio](https://twilio.com) account for SMS services 
- A Gmail account for email services

## Setup
### Using Docker and Docker Compose
1. Clone the repository
   ```bash 
    https://github.com/jeremiasadriano/alert-nature
    cd alert-nature
   ```
   
2. Rename the `.env.example` file to `.env` edit it with the appropriate values:
    ```env
    APP.BD="jdbc:postgresql://db:5432/your_database"
    APP.BD.USER="your_user"
    APP.BD.PASS="your_password"
    APP.SECRET.KEY="your_auth_secret_key"
    APP.SECRET.EXPIRATION=3600
    APP.SERVICES.SMS.ACCOUNT-SID="your_account_sid" //twilo
    APP.SERVICES.SMS.AUTH-TOKEN="your_auth_token"   //twilo
    APP.SERVICES.SMS.NUMBER-FROM="your_sms_number"  //twilo
    SPRING.MAIL.HOST="smtp.gmail.com"               //Gmail
    SPRING.MAIL.PORT=587                            //Gmail
    SPRING.MAIL.USER="your_email_user"              //Gmail
    SPRING.MAIL.PASS="your_email_services_password" //Gmail
    SPRING.MAIL.SENDER-NAME="Your Name"             //Gmail
    ```
3. Build and run the application
    ```bash
    docker-compose up --build
    ```
    Also you can download the application image from [docker hub](https://hub.docker.com/r/godalway/alert-nature):
     ```bash
      docker pull godalway/alert-nature:1.0
      ```

### Manual Setup
1. Clone the repository
   ```bash 
    https://github.com/jeremiasadriano/alert-nature
    cd alert-nature
   ```

2. Rename the `.env.example` file to `.env` edit it with the appropriate values:
    ```env
    APP.BD="jdbc:postgresql://db:5432/your_database"
    APP.BD.USER="your_user"
    APP.BD.PASS="your_password"
    APP.SECRET.KEY="your_auth_secret_key"
    APP.SECRET.EXPIRATION=3600
    APP.SERVICES.SMS.ACCOUNT-SID="your_account_sid" //twilo
    APP.SERVICES.SMS.AUTH-TOKEN="your_auth_token"   //twilo
    APP.SERVICES.SMS.NUMBER-FROM="your_sms_number"  //twilo
    SPRING.MAIL.HOST="smtp.gmail.com"               //Gmail
    SPRING.MAIL.PORT=587                            //Gmail
    SPRING.MAIL.USER="your_email_user"              //Gmail
    SPRING.MAIL.PASS="your_email_services_password" //Gmail
    SPRING.MAIL.SENDER-NAME="Your Name"             //Gmail
    ```
3. Run PostgreSQL 

   Install and start PostgreSQL manually if you haven't already. Make sure to create the database and user specified in the `.env` file.
4. Compile and run the application
   
    If you are using IntelliJ IDEA, you can start the application by clicking the Run button.

    If you do not have IntelliJ IDEA, you can compile and run the application using the command line:
    ``` bash
   mvn clean install
   mvn spring-boot:run
   ```
   If you got any maven error, try:
    ``` bash
    mvn clean install -DskipTests
    mvn spring-boot:run
   ```

## API Documentation
The API is documented using Swagger. Once the application is running, you can access the Swagger UI to explore and test the endpoints.
- Swagger UI: http://localhost:8080/swagger-ui/index.html
