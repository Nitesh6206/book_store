package com.niteshkr.online_book_store.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseRequestDTO {
    private LocalDateTime purchasedDate;
    private List<PurchaseDetailRequestDTO> purchaseDetails;
}
