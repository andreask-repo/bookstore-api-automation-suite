package com.bookstoredemo.utils;

import java.util.ResourceBundle;

public class ResourcesLoader {

    private static final ResourceBundle configs = ResourceBundle.getBundle("config");

    public static String getBaseUrl() {
        return configs.getString("base.url");
    }

    public static String getAuthorsEndpoint() {
        return configs.getString("authors.endpoint");
    }

    public static String getAuthorsByIdEndpoint() {
        return configs.getString("authorsById.endpoint");
    }

    public static String getBooksEndpoint() {
        return configs.getString("books.endpoint");
    }

    public static String getBooksByIdEndpoint() {
        return configs.getString("booksById.endpoint");
    }
}
