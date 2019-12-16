package com.github.al.todobackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URI;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Todo {

    @Id
    private UUID id;

    private String title;

    private Boolean completed;

    private URI url;

    @Column(name = "TODO_ORDER")
    private Long order;
}
