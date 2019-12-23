package com.github.al.todobackend;

import com.github.al.todobackend.api.v1.TodoOperations;
import io.micronaut.http.client.annotation.Client;

@Client("/todos")
public interface TodoClient extends TodoOperations {
}
