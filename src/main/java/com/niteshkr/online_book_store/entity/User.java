package com.niteshkr.online_book_store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.niteshkr.online_book_store.helper.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="username")
    private  String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Enumerated(EnumType.STRING) // Store enum as STRING in the database
    private UserRole role;

    @Column(name="email")
    private  String email;


    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Changed to LAZY for performance
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private CustomerDetails customer;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Changed to LAZY for performance
    private List<Authority> authorities = new ArrayList<>(); // Initialized to avoid NullPointerException
}