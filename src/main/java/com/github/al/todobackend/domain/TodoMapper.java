package com.github.al.todobackend.domain;

import com.github.al.todobackend.api.v1.CreateTodoCommand;
import com.github.al.todobackend.api.v1.TodoDTO;
import com.github.al.todobackend.api.v1.UpdateTodoCommand;
import io.micronaut.http.uri.UriBuilder;

import javax.inject.Singleton;
import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class TodoMapper {

    public TodoDTO toDTO(Todo todo) {
        return TodoDTO.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .completed(todo.getCompleted())
                .url(todo.getUrl())
                .order(todo.getOrder())
                .build();
    }

    public Iterable<TodoDTO> toDTO(Iterable<Todo> todos) {
        return StreamSupport.stream(todos.spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Todo fromCreateCommand(CreateTodoCommand command, String basePath) {
        UUID id = UUID.randomUUID();
        Boolean completed = command.getCompleted() == null ? Boolean.FALSE : command.getCompleted();
        URI uri = UriBuilder.of(basePath).path(id.toString()).build();
        return new Todo(id, command.getTitle(), completed, uri, command.getOrder());
    }

    public Todo fromUpdateCommand(UpdateTodoCommand command, Todo oldTodo) {
        String title = command.getTitle() == null ? oldTodo.getTitle() : command.getTitle();
        Boolean completed = command.getCompleted() == null ? oldTodo.getCompleted() : command.getCompleted();
        Long order = command.getOrder() == null ? oldTodo.getOrder() : command.getOrder();
        return new Todo(oldTodo.getId(), title, completed, oldTodo.getUrl(), order);
    }

}

