package com.github.al.todobackend.api.v1;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Introspected
public class UpdateTodoCommand {

    private String title;
    private Boolean completed;
    private Long order;

}
