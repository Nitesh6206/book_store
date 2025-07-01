package com.niteshkr.online_book_store.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsDTO {
    private Long id;
    private long mobile;
    private String address;
}
