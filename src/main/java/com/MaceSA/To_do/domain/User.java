package com.MaceSA.To_do.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Table(name = "user_account")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private  String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private UserRoles rol;


    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Task> userTasks;


}
