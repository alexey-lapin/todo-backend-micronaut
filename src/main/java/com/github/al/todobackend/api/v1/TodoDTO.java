package com.github.al.todobackend.api.v1;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@Getter
@Builder
@ToString
@Introspected
public class TodoDTO {

    @NotNull
    private UUID id;
    @NotNull
    private String title;
    private Boolean completed;
    private URI url;
    private Long order;

}
