package com.niteshkr.online_book_store.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="shopping_cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  Id ;

    @JoinColumn(name="customer_id", referencedColumnName="id")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH})
    private CustomerDetails customer;


    @JoinColumn(name="book_id", referencedColumnName="id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH})
    private Book book;

    @Column(name = "count")
    private int quantity;


}
