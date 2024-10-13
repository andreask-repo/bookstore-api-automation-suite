# Bookstore API Automation Framework

## How-To
In order to execute the automated scenarios the following steps must be followed:
1. Clone the following public repository from GitHub -> https://github.com/andreask-repo/bookstore-api-tests.git
2. Reload Maven for all the maven dependencies to be fetched
3. Execute 'mvn test' from console or navigate to src/test/java/com/bookstoredemo/books/tests/BooksTests.java and execute the tests from Class level

## Project Structure
The project is structured as:
- In src/main/java/com/bookstoredemo there are three directories where the first two contain the API calls for each of the API categories (Books/Authors) and the third any helper/utility methods
  - authors/api contains the RestAssured calls for the requested Authors API calls
  - authors/models contains the Authors POJO
  - books/api contains the RestAssured calls for the requested Books API calls
  - books/models contains the Books POJO
  - utils contains a class that loads the configured endpoints from src/main/resources
- In src/main/resources there are located the following files:
  - config.properties, containing the base URL and the respective Books and Authors endpoints
  - log4j2.xml, containing the configuration for the logging mechanism
- In test/java/com/bookstoredemo there are the four following directories:
  - authors/tests that contains a placeholder class for the Author scenarios (not requested)
  - books/tests that contains all the tests performed on Books APIs
  - base that contains a class that is inherited by the respective Books and Authors test classes and implements the report configuration
  - utils that contains a Utilities class with helper methods of the test classes

## Notes
1. As requested from the instructions, only scenarios for the Books API set were implemented
2. The main structure for Authors API was included as well
3. As it may be observed from the remote repository, several branches, commits and PRs were used to simulate a real-life environment
4. (Important) Some tests will fail and this is to demonstrate that the scenarios are failing as the API is not well-implemented (e.g. Adding a new book with an existing ID)
5. A generated report can be found under root directory of the remote repository with name "BooksApiReport.html"
6. In the root directory a Jenkins file is located but the Jenkins integration was not successful