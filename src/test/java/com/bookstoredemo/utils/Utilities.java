package com.bookstoredemo.utils;

import com.bookstoredemo.books.models.BookFailedResponseDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;

public class Utilities {

    public static JsonObject convertResponseBodyToJson(String responseBody)
    {
        return JsonParser.parseString(responseBody).getAsJsonObject();
    }

    public static int getResponseObjectCount(Response response)
    {
        JsonArray jsonArray = JsonParser.parseString(response.asString()).getAsJsonArray();
        return jsonArray.size();
    }

    public static BookFailedResponseDto getFailedResponseDto(Response response)
    {
        return new Gson().fromJson(
                Utilities.convertResponseBodyToJson(response.body().asString()),
                BookFailedResponseDto.class);
    }
}
