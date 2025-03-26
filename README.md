# Email Validator
**Email Validator** - is an API for grouping a list of emails into valid and invalid groups.
## Setup instructions

 1. **Download Java and Maven** - **Java OpenJDK 23.0.2** &&  **Apache Maven 3.9.9**
 2. **Clone the project**: `git@gitlab.griddynamics.net:mpavliuk/email-validator.git`
 3. **Build and test** - use `mvn clean install` to ensure that all the dependencies have been downloaded, and the main parts of the application are working on your machine
 4. **Start the server** - use `mvn spring-boot:run`  to start the server. You should see the spring logo in ASCII art and the message: **Started EmailValidatorApplication in 1.415 seconds (process running for 1.724)**. You should also see a message saying what port the server was opened on, it will be useful for later (default is 8080);
 5. **Use the API** - for the purposes of giving an example I'll talk about Postman, but you obviously can do whatever you want with it from now, maybe connect it to your app.
## Usage examples
In Postman create a new **GET** request with the address - `localhost:8080/validate/`
|           | Headers | Query Parameters | Request Body                              | Authorization |
|-----------|---------|------------------|-------------------------------------------|---------------|
| Required: | No      | No               | Yes                                       | No            |
| Values:   | --      | --               | "emails" - array of emails for validation |               |
| Example:  | --      | --               | {"emails": ["exampleEmail1@gmail.com"]}   | --            |
 ## Testing instructions
 To run tests use `mvn clean test`.
 What is covered: 
 
 6. **Unit tests** - possible inputs with unsupported values (Whitespaces, Control characters, email length, empty request bodies).
 7. **Integration Tests** - controllers integration with the service.
 8.  **API tests** - uses RestAssured. Verifies status codes, response bodies.
 
## **Error handling**
Errors will be show by 400 status code and a specific message. 
 1. **Empty request body** - Error: Request body is missing. Try {"emails":[...]}
 2. **Missing emails field** - Error: Not a valid request body. Try {"emails":[...]}
 3. **Empty emails array** - Error: Empty list in the request body. Try {"emails":["email1"]}
 4. **Control characters** - Error: Email has control characters. Please remove them.
 5. **Whitespaces** - Error: Email has whitespaces. Please remove them.
