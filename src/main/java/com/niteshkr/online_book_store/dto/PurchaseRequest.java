package com.niteshkr.online_book_store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequest {
    private int bookId;
    private int quantity;

}