package com.niteshkr.online_book_store.dto;


import com.niteshkr.online_book_store.entity.Book;
import lombok.Data;

@Data
public class PurchaseDetailDTO {
    private Long id;
    private Book book;
    private double price;
    private int quantity;
}