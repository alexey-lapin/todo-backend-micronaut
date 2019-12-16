package com.github.al.todobackend.controller;

import com.github.al.todobackend.api.v1.CreateTodoCommand;
import com.github.al.todobackend.api.v1.TodoDTO;
import com.github.al.todobackend.api.v1.TodoOperations;
import com.github.al.todobackend.api.v1.UpdateTodoCommand;
import com.github.al.todobackend.domain.Todo;
import com.github.al.todobackend.domain.TodoMapper;
import com.github.al.todobackend.domain.TodoRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Validated
@Controller("/todos")
public class TodoController implements TodoOperations {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    private final HttpHostResolver httpHostResolver;

    @Override
    public Iterable<TodoDTO> findAll() {
        return todoMapper.toDTO(todoRepository.findAll());
    }

    @Override
    public Optional<TodoDTO> findById(@NotNull UUID id) {
        return todoRepository.findById(id).map(todoMapper::toDTO);
    }

    @Override
    public HttpResponse<TodoDTO> create(@Body @NotNull @Valid CreateTodoCommand body, HttpRequest<?> httpRequest) {
        String basePath = httpHostResolver.resolve(httpRequest) + httpRequest.getPath();
        Todo todo = todoMapper.fromCreateCommand(body, basePath);
        Todo result = todoRepository.save(todo);
        return HttpResponse.created(todoMapper.toDTO(result), createdUri(httpRequest, result));
    }

    @Override
    public HttpResponse<TodoDTO> updateById(@NotNull UUID id, @Body @NotNull UpdateTodoCommand request) {
        Todo oldTodo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("not exists"));
        Todo todo = todoMapper.fromUpdateCommand(request, oldTodo);
        Todo result = todoRepository.update(todo);
        return HttpResponse.ok(todoMapper.toDTO(result));
    }

    @Override
    public HttpResponse<String> deleteAll() {
        todoRepository.deleteAll();
        return HttpResponse.noContent();
    }

    @Override
    public HttpResponse<String> deleteById(@NotNull UUID id) {
        todoRepository.deleteById(id);
        return HttpResponse.noContent();
    }

    @Override
    public HttpResponse<String> optionsById() {
        return HttpResponse.ok();
    }

    private URI createdUri(HttpRequest<?> http, Todo todo) {
        return UriBuilder.of(http.getUri()).path(todo.getId().toString()).build();
    }
}
