package com.niteshkr.online_book_store.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseRequestDTO {
    private LocalDateTime purchasedDate;
    private List<PurchaseDetailDTO> purchaseDetails;
}
