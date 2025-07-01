package com.niteshkr.online_book_store.controller;

import com.niteshkr.online_book_store.entity.Book;
import com.niteshkr.online_book_store.entity.PurchaseHistory;
import com.niteshkr.online_book_store.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<Book>> getPurchasedBooks() {
            List<Book> purchasedBooks = customerService.getPurchasedBooks();
            return new ResponseEntity<>(purchasedBooks, HttpStatus.OK);
    }

    @PostMapping("/purchases")
    public ResponseEntity<PurchaseHistory> createPurchase(@RequestBody PurchaseHistory purchaseHistory) {
            PurchaseHistory createdPurchase = customerService.createPurchasedOrder(purchaseHistory);
            return new ResponseEntity<>(createdPurchase, HttpStatus.CREATED);
    }

    @PutMapping("/purchases/{purchaseId}")
    public ResponseEntity<PurchaseHistory> updatePurchase(@PathVariable Long purchaseId, @Valid @RequestBody PurchaseHistory purchaseHistory) {
            PurchaseHistory updatedPurchase = customerService.updatePurchaseHistory(purchaseId, purchaseHistory);
            return new ResponseEntity<>(updatedPurchase, HttpStatus.OK);

    }

    @DeleteMapping("/purchases/{purchaseId}")
    public ResponseEntity<String> deletePurchase(@PathVariable Long purchaseId) {
            customerService.deletePurchaseHistory(purchaseId);
            return new ResponseEntity<>("Purchase history deleted successfully", HttpStatus.OK);
    }
}