package com.bookstoredemo.books.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.bookstoredemo.base.TestBase;
import com.bookstoredemo.books.api.BooksApi;
import com.bookstoredemo.books.models.BookFailedResponseDto;
import com.bookstoredemo.books.models.BookGenericDto;
import com.bookstoredemo.utils.Utilities;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooksTests extends TestBase {

    private static final Logger logger = LogManager.getLogger(BooksTests.class);
    private final BooksApi booksApi = new BooksApi();
    private ExtentTest extentTest;

    private static final String NOT_FOUND_ERROR = "Not Found";
    private static final String VALIDATION_ERROR = "One or more validation errors occurred.";
    private static final String INVALID_ID_ERROR = "The value '11111111111111' is not valid.";

    @Test
    public void testGetAllBooks()
    {
        extentTest = extentReports.createTest("GET All Books API Test");
        try
        {
            logger.debug("----------------------- Entering testGetAllBooks Test -----------------------");
            Response response = booksApi.getAllBooks();
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(200, response.getStatusCode(), "Status code not the expected");
            Assertions.assertTrue(response.getHeader("Content-Type").contains(ContentType.JSON.toString()), "application/json expected as Header");
            Assertions.assertTrue(Utilities.getResponseObjectCount(response) > 0);
            logger.debug("----------------------- Exiting testGetAllBooks Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testGetBookById()
    {
        extentTest = extentReports.createTest("GET Book By ID API Test");
        try
        {
            logger.debug("----------------------- Entering testGetBookById Test -----------------------");
            Response response = booksApi.getBookById("1");
            BookGenericDto book = new Gson().fromJson(Utilities.convertResponseBodyToJson(response.body().asString()), BookGenericDto.class);
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(200, response.getStatusCode(), "Status code not the expected");
            Assertions.assertTrue(response.getHeader("Content-Type").contains(ContentType.JSON.toString()), "application/json expected as Header");
            Assertions.assertNotNull(book.getId(), "ID should not be null");
            Assertions.assertEquals(1, book.getId());
            Assertions.assertNotNull(book.getTitle(), "Title should not be null");
            Assertions.assertNotNull(book.getDescription(), "Description should not be null");
            Assertions.assertNotNull(book.getPageCount(), "Page Count should not be null");
            Assertions.assertNotNull(book.getExcerpt(), "Excerpt should not be null");
            Assertions.assertNotNull(book.getPublishDate(), "Publish Date should not be null");
            logger.debug("----------------------- Exiting testGetBookById Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testGetBookByNonExistingId()
    {
        extentTest = extentReports.createTest("GET Book By Non-Existing ID API Test");
        try
        {
            logger.debug("----------------------- Entering testGetBookByNonExistingId Test -----------------------");
            Response response = booksApi.getBookById("0");
            BookFailedResponseDto bookResponseDto = new Gson().fromJson(
                    Utilities.convertResponseBodyToJson(response.body().asString()),
                    BookFailedResponseDto.class);
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(404, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(NOT_FOUND_ERROR, bookResponseDto.getTitle());
            logger.debug("----------------------- Exiting testGetBookByNonExistingId Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * The following test validates that a very large number is handled from the API
     */
    @Test
    public void testGetBookByInvalidId()
    {
        extentTest = extentReports.createTest("GET Book By Invalid ID API Test");
        try
        {
            logger.debug("----------------------- Entering testGetBookByInvalidId Test -----------------------");
            Response response = booksApi.getBookById("11111111111111");
            BookFailedResponseDto bookResponseDto = Utilities.getFailedResponseDto(response);
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(VALIDATION_ERROR, bookResponseDto.getTitle());
            Assertions.assertEquals(INVALID_ID_ERROR, bookResponseDto.getErrors().getId().get(0));
            logger.debug("----------------------- Exiting testGetBookByInvalidId Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testAddNewBook()
    {
        extentTest = extentReports.createTest("POST Add New Book API Test");
        try
        {
            logger.debug("----------------------- Entering testAddNewBook Test -----------------------");

            BookGenericDto newBook = new BookGenericDto(
                    1000,
                    "My New Book",
                    "Testing POST call for new book Addition",
                    1,
                    "Some text here",
                    Utilities.getDateTime()
            );
            logger.debug("Payload: {}", new Gson().toJson(newBook));

            Response response = booksApi.addNewBook(newBook);
            logger.debug("Response: {}", response.asString());
            Assertions.assertEquals(200, response.getStatusCode(), "Status code not the expected");
            logger.debug("----------------------- Exiting testAddNewBook Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }


    @Test
    public void testAddNewBookNullValues()
    {
        extentTest = extentReports.createTest("POST Add New Book With Null Values API Test");
        try
        {
            logger.debug("----------------------- Entering testAddNewBookNullValues Test -----------------------");

            BookGenericDto newBook = new BookGenericDto(
                    1000,
                    "My New Book",
                    "Testing POST call for new book Addition",
                    1,
                    "Some text here",
                    Utilities.getDateTime()
            );

            BookGenericDto nullBookValues = newBook;
            nullBookValues.setId(null);
            Response response = booksApi.addNewBook(nullBookValues);
            logger.debug("Response: {}", response.asString());
            BookFailedResponseDto bookResponseDto = Utilities.getFailedResponseDto(response);
            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(VALIDATION_ERROR, bookResponseDto.getTitle());
            nullBookValues.setId(newBook.getId());

            nullBookValues.setPageCount(null);
            response = booksApi.addNewBook(nullBookValues);
            logger.debug("Response: {}", response.asString());
            bookResponseDto = Utilities.getFailedResponseDto(response);
            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(VALIDATION_ERROR, bookResponseDto.getTitle());
            nullBookValues.setPageCount(newBook.getPageCount());

            nullBookValues.setPublishDate(null);
            response = booksApi.addNewBook(nullBookValues);
            logger.debug("Response: {}", response.asString());
            bookResponseDto = Utilities.getFailedResponseDto(response);
            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(VALIDATION_ERROR, bookResponseDto.getTitle());
            logger.debug("----------------------- Exiting testAddNewBookNullValues Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testAddNewBookWithExistingId()
    {
        extentTest = extentReports.createTest("POST Add New Book With Existing ID API Test");
        try
        {
            logger.debug("----------------------- Entering testAddNewBookWithExistingId Test -----------------------");

            BookGenericDto newBook = new BookGenericDto(
                    1,
                    "My New Book",
                    "Testing POST call for new book Addition",
                    1,
                    "Some text here",
                    Utilities.getDateTime()
            );
            Response response = booksApi.addNewBook(newBook);
            logger.debug("Response: {}", response.asString());
            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            logger.debug("----------------------- Exiting testAddNewBookWithExistingId Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testUpdateBookById()
    {
        extentTest = extentReports.createTest("PUT Update Book By ID API Test");
        try
        {
            logger.debug("----------------------- Entering testUpdateBookById Test -----------------------");

            BookGenericDto newBookDetails = new BookGenericDto(
                    1,
                    "My Updated Book",
                    "Testing PUT call for existing book update",
                    1,
                    "Some text here",
                    Utilities.getDateTime()
            );
            Response response = booksApi.updateBook("1", newBookDetails);
            BookGenericDto book = new Gson().fromJson(Utilities.convertResponseBodyToJson(response.body().asString()), BookGenericDto.class);
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(200, response.getStatusCode(), "Status code not the expected");
            Assertions.assertTrue(response.getHeader("Content-Type").contains(ContentType.JSON.toString()), "application/json expected as Header");
            Assertions.assertNotNull(book.getId(), "ID should not be null");
            Assertions.assertEquals(1, book.getId());
            Assertions.assertNotNull(book.getTitle(), "Title should not be null");
            Assertions.assertNotNull(book.getDescription(), "Description should not be null");
            Assertions.assertNotNull(book.getPageCount(), "Page Count should not be null");
            Assertions.assertNotNull(book.getExcerpt(), "Excerpt should not be null");
            Assertions.assertNotNull(book.getPublishDate(), "Publish Date should not be null");
            logger.debug("----------------------- Exiting testUpdateBookById Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testUpdateBookByNonExistingId()
    {
        extentTest = extentReports.createTest("PUT Update Book By Non-Existing ID API Test");
        try
        {
            logger.debug("----------------------- Entering testUpdateBookByNonExistingId Test -----------------------");
            BookGenericDto newBookDetails = new BookGenericDto(
                    1,
                    "My Updated Book",
                    "Testing PUT call for existing book update",
                    1,
                    "Some text here",
                    Utilities.getDateTime()
            );
            Response response = booksApi.updateBook("0", newBookDetails);
            BookFailedResponseDto bookResponseDto = new Gson().fromJson(
                    Utilities.convertResponseBodyToJson(response.body().asString()),
                    BookFailedResponseDto.class);
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(404, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(NOT_FOUND_ERROR, bookResponseDto.getTitle());
            logger.debug("----------------------- Exiting testUpdateBookByNonExistingId Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * The following test validates that a very large number is handled from the API
     */
    @Test
    public void testUpdateBookByInvalidId()
    {
        extentTest = extentReports.createTest("PUT Update Book By Invalid ID API Test");
        try
        {
            logger.debug("----------------------- Entering testUpdateBookByInvalidId Test -----------------------");
            BookGenericDto newBookDetails = new BookGenericDto(
                    1,
                    "My Updated Book",
                    "Testing PUT call for existing book update",
                    1,
                    "Some text here",
                    Utilities.getDateTime()
            );
            Response response = booksApi.updateBook("11111111111111", newBookDetails);
            BookFailedResponseDto bookResponseDto = Utilities.getFailedResponseDto(response);
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(VALIDATION_ERROR, bookResponseDto.getTitle());
            Assertions.assertEquals(INVALID_ID_ERROR, bookResponseDto.getErrors().getId().get(0));
            logger.debug("----------------------- Exiting testUpdateBookByInvalidId Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testUpdateBookNullValues()
    {
        extentTest = extentReports.createTest("PUT Update Book With Null Values API Test");
        try
        {
            logger.debug("----------------------- Entering testUpdateBookNullValues Test -----------------------");

            BookGenericDto newBookDetails = new BookGenericDto(
                    1,
                    "My Updated Book",
                    "Testing PUT call for existing book update",
                    1,
                    "Some text here",
                    Utilities.getDateTime()
            );

            BookGenericDto nullBookValues = newBookDetails;
            nullBookValues.setId(null);
            Response response = booksApi.updateBook(String.valueOf(newBookDetails.getId()), nullBookValues);
            logger.debug("Response: {}", response.asString());
            BookFailedResponseDto bookResponseDto = Utilities.getFailedResponseDto(response);
            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(VALIDATION_ERROR, bookResponseDto.getTitle());
            nullBookValues.setId(newBookDetails.getId());

            nullBookValues.setPageCount(null);
            response = booksApi.updateBook(String.valueOf(newBookDetails.getId()), nullBookValues);
            logger.debug("Response: {}", response.asString());
            bookResponseDto = Utilities.getFailedResponseDto(response);
            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(VALIDATION_ERROR, bookResponseDto.getTitle());
            nullBookValues.setPageCount(newBookDetails.getPageCount());

            nullBookValues.setPublishDate(null);
            response = booksApi.updateBook(String.valueOf(newBookDetails.getId()), nullBookValues);
            logger.debug("Response: {}", response.asString());
            bookResponseDto = Utilities.getFailedResponseDto(response);
            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(VALIDATION_ERROR, bookResponseDto.getTitle());
            logger.debug("----------------------- Exiting testUpdateBookNullValues Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testDeleteBookById()
    {
        extentTest = extentReports.createTest("DELETE Book By ID API Test");
        try
        {
            logger.debug("----------------------- Entering testDeleteBookById Test -----------------------");
            Response response = booksApi.deleteBook("1");
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(200, response.getStatusCode(), "Status code not the expected");
            logger.debug("----------------------- Exiting testDeleteBookById Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testDeleteBookByNonExistingId()
    {
        extentTest = extentReports.createTest("DELETE Book By Non-Existing ID API Test");
        try
        {
            logger.debug("----------------------- Entering testDeleteBookByNonExistingId Test -----------------------");
            Response response = booksApi.getBookById("0");
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(404, response.getStatusCode(), "Status code not the expected");
            logger.debug("----------------------- Exiting testDeleteBookByNonExistingId Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * The following test validates that a very large number is handled from the API
     */
    @Test
    public void testDeleteBookByInvalidId()
    {
        extentTest = extentReports.createTest("DELETE Book By Invalid ID API Test");
        try
        {
            logger.debug("----------------------- Entering testDeleteBookByInvalidId Test -----------------------");
            Response response = booksApi.getBookById("11111111111111");
            BookFailedResponseDto bookResponseDto = Utilities.getFailedResponseDto(response);
            logger.debug("Response: {}", response.asString());

            Assertions.assertEquals(400, response.getStatusCode(), "Status code not the expected");
            Assertions.assertEquals(VALIDATION_ERROR, bookResponseDto.getTitle());
            Assertions.assertEquals(INVALID_ID_ERROR, bookResponseDto.getErrors().getId().get(0));
            logger.debug("----------------------- Exiting testDeleteBookByInvalidId Test -----------------------");
            extentTest.log(Status.PASS, "Test passed!");
        }
        catch (AssertionError e)
        {
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }
}
