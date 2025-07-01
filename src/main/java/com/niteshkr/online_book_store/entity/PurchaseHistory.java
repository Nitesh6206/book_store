package com.niteshkr.online_book_store.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name="purchase_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name="customer_id")
    @ManyToOne
    @JsonBackReference
    private CustomerDetails customer;

    private LocalDateTime purchasedDate;

    @OneToMany(mappedBy ="purchaseHistory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<PurchaseDetail> purchaseDetails;


}