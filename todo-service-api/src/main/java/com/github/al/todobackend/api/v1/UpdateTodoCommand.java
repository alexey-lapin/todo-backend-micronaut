package com.github.al.todobackend.api.v1;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;

@Getter
@Introspected
public class UpdateTodoCommand {

    private String title;

    private Boolean completed;

    private Long order;
}
