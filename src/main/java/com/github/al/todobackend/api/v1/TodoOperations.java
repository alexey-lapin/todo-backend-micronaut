package com.github.al.todobackend.api.v1;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface TodoOperations {

    @Get("/")
    Iterable<TodoDTO> findAll();

    @Get("/{id}")
    HttpResponse<TodoDTO> findById(@NotNull UUID id);

    @Post
    HttpResponse<TodoDTO> create(@Body @NotNull @Valid CreateTodoCommand body);

    @Patch("/{id}")
    HttpResponse<TodoDTO> updateById(@NotNull UUID id, @Body @NotNull @Valid UpdateTodoCommand body);

    @Delete("/")
    HttpResponse<String> deleteAll();

    @Delete("/{id}")
    HttpResponse<String> deleteById(@NotNull UUID id);

    @Options("/")
    HttpResponse<String> options();
}
