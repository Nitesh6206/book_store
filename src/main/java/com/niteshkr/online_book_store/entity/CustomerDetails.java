package com.niteshkr.online_book_store.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name="customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="number")
    private long mobile;

    @Column(name="address")
    private String address;


    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="book_user", joinColumns=@JoinColumn(name="customer_id"),
            inverseJoinColumns=@JoinColumn(name="book_id"))
    private Set<Book> books;


    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PurchaseHistory> purchaseHistories;

}
