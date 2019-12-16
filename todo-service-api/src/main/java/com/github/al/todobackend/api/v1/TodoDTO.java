package com.github.al.todobackend.api.v1;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.net.URI;
import java.util.UUID;

@Getter
@Builder
@ToString
@Introspected
public class TodoDTO {

    private UUID id;
    private String title;
    private Boolean completed;
    private URI url;
    private Long order;

}
