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
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

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
    public HttpResponse<TodoDTO> findById(@NotNull UUID id) {
        Optional<Todo> existingTodo = todoRepository.findById(id);
        if (existingTodo.isPresent()) {
            return HttpResponse.ok(todoMapper.toDTO(existingTodo.get()));
        } else {
            return HttpResponse.notFound();
        }
    }

    @Override
    public HttpResponse<TodoDTO> create(@Body @NotNull @Valid CreateTodoCommand body) {
        HttpRequest<Object> httpRequest = ServerRequestContext.currentRequest()
                .orElseThrow(() -> new RuntimeException("no request context available"));
        String basePath = httpHostResolver.resolve(httpRequest) + httpRequest.getPath();
        Todo todo = todoMapper.fromCreateCommand(body, basePath);
        Todo result = todoRepository.save(todo);
        return HttpResponse.created(todoMapper.toDTO(result), createdUri(httpRequest, result));
    }

    @Override
    public HttpResponse<TodoDTO> updateById(@NotNull UUID id, @Body @NotNull @Valid UpdateTodoCommand body) {
        Optional<Todo> existingTodo = todoRepository.findById(id);
        if (existingTodo.isPresent()) {
            Todo todo = todoMapper.fromUpdateCommand(body, existingTodo.get());
            Todo result = todoRepository.update(todo);
            return HttpResponse.ok(todoMapper.toDTO(result));
        } else {
            return HttpResponse.notFound();
        }
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
    public HttpResponse<String> options() {
        return HttpResponse.ok();
    }

    private URI createdUri(HttpRequest<?> http, Todo todo) {
        return UriBuilder.of(http.getUri()).path(todo.getId().toString()).build();
    }

    @Error(status = HttpStatus.NOT_FOUND)
    public HttpResponse<JsonError> notFound(HttpRequest<?> request) {
        JsonError error = new JsonError("Todo Not Found").link(Link.SELF, Link.of(request.getUri()));
        return HttpResponse.<JsonError>notFound().body(error);
    }

}
