package com.bookstoredemo.books.api;

import com.bookstoredemo.books.models.BookGenericDto;
import com.bookstoredemo.utils.ResourcesLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BooksApi {

    private static final String BOOKS_URL = ResourcesLoader.getBaseUrl() + ResourcesLoader.getBooksEndpoint();
    private static final String BOOKS_BY_ID_URL = ResourcesLoader.getBaseUrl() + ResourcesLoader.getBooksByIdEndpoint();

    public Response getAllBooks()
    {
        return RestAssured.given()
                .get(BOOKS_URL);
    }

    public Response getBookById(String id)
    {
        return RestAssured.given()
                .pathParam("id", id)
                .get(BOOKS_BY_ID_URL);
    }

    public Response addNewBook(BookGenericDto newBook)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String body = gson.toJson(newBook);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(BOOKS_URL);
    }

    public Response updateBook(int id, BookGenericDto updateBookDetails)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String body = gson.toJson(updateBookDetails);
        return RestAssured.given()
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(body)
                .put(BOOKS_BY_ID_URL);
    }

    public Response deleteBook(int id)
    {
        return RestAssured.given()
                .pathParam("id", id)
                .delete(BOOKS_BY_ID_URL);
    }
}
