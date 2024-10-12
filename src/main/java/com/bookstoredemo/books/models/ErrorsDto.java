package com.bookstoredemo.books.models;

import java.util.List;

public class ErrorsDto {
    private List<String> id;

    public ErrorsDto() {}

    public ErrorsDto(List<String> id)
    {
        this.id = id;
    }

    public List<String> getId()
    {
        return id;
    }

    public void setId(List<String> id)
    {
        this.id = id;
    }
}
