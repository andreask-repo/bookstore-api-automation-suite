package com.bookstoredemo.authors.api;

import com.bookstoredemo.authors.models.Author;
import com.bookstoredemo.utils.ResourcesLoader;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthorsApi {

    private static final String AUTHORS_URL = ResourcesLoader.getBaseUrl() + ResourcesLoader.getAuthorsEndpoint();
    private static final String AUTHORS_BY_ID_URL = ResourcesLoader.getBaseUrl() + ResourcesLoader.getAuthorsByIdEndpoint();

    public Response getAllAuthors()
    {
        return RestAssured.given()
                .get(AUTHORS_URL);
    }

    public Response getAuthorById(int id)
    {
        return RestAssured.given()
                .pathParam("id", id)
                .get(AUTHORS_BY_ID_URL);
    }

    public Response addNewAuthor(Author newAuthor)
    {
        return RestAssured.given()
                .body(newAuthor)
                .post(AUTHORS_URL);
    }

    public Response updateAuthor(int id, Author newAuthor)
    {
        return RestAssured.given()
                .pathParam("id", id)
                .body(newAuthor)
                .put(AUTHORS_BY_ID_URL);
    }

    public Response deleteAuthor(int id)
    {
        return RestAssured.given()
                .pathParam("id", id)
                .delete(AUTHORS_BY_ID_URL);
    }
}
