package com.niteshkr.online_book_store.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class PurchaseRequest {
    private int bookId;
    private int quantity;

}