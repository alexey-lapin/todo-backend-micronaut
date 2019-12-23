package com.github.al.todobackend.controller;

import com.github.al.todobackend.TodoClient;
import com.github.al.todobackend.api.v1.CreateTodoCommand;
import com.github.al.todobackend.api.v1.TodoDTO;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class TodoControllerTest {

    @Inject
    TodoClient todoClient;

    @BeforeEach
    void beforeEach() {
        todoClient.deleteAll();
    }

//    @MockBean(TodoRepository.class)
//    TodoRepository todoRepository() {
//        return mock(TodoRepository.class);
//    }

    @Test
    void should_findAllReturnEmptyCollection() {
        Iterable<TodoDTO> todos = todoClient.findAll();

        assertThat(todos).isEmpty();
    }

    @Test
    void should_createReturnCorrectTodo() {
        TodoDTO todo = todoClient.create(CreateTodoCommand.builder().title("title1").build()).body();

        assertThat(todo.getId()).isNotNull();
        assertThat(todo.getTitle()).isEqualTo("title1");
        assertThat(todo.getCompleted()).isFalse();
        assertThat(todo.getUrl()).isNotNull();
    }

    @Test
    void should_findAllReturnCollectionOfCorrectSize() {
        TodoDTO todo1 = todoClient.create(CreateTodoCommand.builder().title("title1").build()).body();
        TodoDTO todo2 = todoClient.create(CreateTodoCommand.builder().title("title2").build()).body();

        Iterable<TodoDTO> todos = todoClient.findAll();

        assertThat(todos).hasSize(2);
    }
}