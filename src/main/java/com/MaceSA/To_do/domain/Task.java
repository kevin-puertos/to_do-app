package com.MaceSA.To_do.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idTask;

    @Column(nullable = false)
    private String taskName;
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    private LocalDate creationDate;
    private LocalDate expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore //
    private User user;

    public Task() {
        this.creationDate = LocalDate.now();
    }

    public Task(Long idTask, String taskName, String description, LocalDate expireDate, TaskStatus status) {
        this.idTask = idTask;
        this.taskName = taskName;
        this.creationDate = LocalDate.now();
        this.description = description;
        this.expireDate = expireDate;
        this.status = status;
    }

    public Task(User user, LocalDate expireDate, TaskStatus status, String description, String taskName) {
        this.user = user;
        this.expireDate = expireDate;
        this.creationDate = LocalDate.now();
        this.status = status;
        this.description = description;
        this.taskName = taskName;
    }

    public Task(String taskName, String description, LocalDate expireDate, TaskStatus status) {
        this.expireDate = expireDate;
        this.status = status;
        this.creationDate = LocalDate.now();
        this.description = description;
        this.taskName = taskName;
    }
}
