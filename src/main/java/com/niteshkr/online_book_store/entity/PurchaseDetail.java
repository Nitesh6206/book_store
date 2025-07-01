package com.niteshkr.online_book_store.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="purchase_detail")
@Setter
@Getter
public class PurchaseDetail{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="purchase_history_id")
    @JsonBackReference
    PurchaseHistory purchaseHistory;


    @JoinColumn(name="book_id")
    @OneToOne
    Book book;


    @Column(name = "price")
    double price;

    @Column(name = "quantity")
    int quantity;


}
