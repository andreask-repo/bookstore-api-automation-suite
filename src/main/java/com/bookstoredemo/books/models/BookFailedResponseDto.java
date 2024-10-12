package com.bookstoredemo.books.models;

public class BookFailedResponseDto {
    private String type;
    private String title;
    private int status;
    private String traceId;
    private ErrorsDto errors;

    public BookFailedResponseDto() {}

    public BookFailedResponseDto(String type, String title, int status, String traceId, ErrorsDto errors)
    {
        this.type = type;
        this.title = title;
        this.status = status;
        this.traceId = traceId;
        this.errors = errors;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getTraceId()
    {
        return traceId;
    }

    public void setTraceId(String traceId)
    {
        this.traceId = traceId;
    }

    public ErrorsDto getErrors()
    {
        return errors;
    }

    public void setErrors(ErrorsDto errors)
    {
        this.errors = errors;
    }
}


