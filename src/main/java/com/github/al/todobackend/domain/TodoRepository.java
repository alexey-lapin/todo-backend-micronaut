package com.github.al.todobackend.domain;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

import java.util.UUID;

@Repository
public interface TodoRepository extends PageableRepository<Todo, UUID> {
}
