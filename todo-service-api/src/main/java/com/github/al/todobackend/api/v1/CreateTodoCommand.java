package com.github.al.todobackend.api.v1;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Introspected
public class CreateTodoCommand {

    @NotBlank
    private String title;

    private Boolean completed;

    private Long order;
}
