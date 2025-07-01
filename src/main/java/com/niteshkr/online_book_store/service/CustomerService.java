package com.niteshkr.online_book_store.service;

import com.niteshkr.online_book_store.Repository.BookRepository;
import com.niteshkr.online_book_store.Repository.PurchaseHistoryRepository;
import com.niteshkr.online_book_store.Repository.UserRepository;
import com.niteshkr.online_book_store.entity.Book;
import com.niteshkr.online_book_store.entity.PurchaseDetail;
import com.niteshkr.online_book_store.entity.PurchaseHistory;
import com.niteshkr.online_book_store.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public CustomerService(PurchaseHistoryRepository purchaseHistoryRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Book> getPurchasedBooks() {
        User user = getAuthenticatedUser();

        List<PurchaseHistory> histories = purchaseHistoryRepository.findByCustomerUser(user);
        return histories.stream()
                .flatMap(h -> h.getPurchaseDetails().stream())
                .map(PurchaseDetail::getBook)
                .distinct()
                .collect(Collectors.toList());
    }

    public PurchaseHistory createPurchasedOrder(PurchaseHistory purchaseHistory) {
        User user = getAuthenticatedUser();

        if (user.getCustomer() == null) {
            throw new IllegalArgumentException("Customer details not found for user");
        }

        purchaseHistory.setCustomer(user.getCustomer());

        if (purchaseHistory.getPurchaseDetails().size() != 1) {
            throw new IllegalArgumentException("Only one purchase detail is supported per order");
        }

        PurchaseDetail detail = purchaseHistory.getPurchaseDetails().iterator().next();
        Book book = getBookById(detail.getBook().getId());

        if (book.getQuantity() < detail.getQuantity()) {
            throw new IllegalArgumentException("Insufficient book quantity");
        }

        double expectedPrice = book.getPrice() * detail.getQuantity();
        if (expectedPrice<detail.getPrice()) {
            throw new IllegalArgumentException("Price mismatch with quantity");
        }

        book.setQuantity(book.getQuantity() - detail.getQuantity());
        bookRepository.save(book);

        detail.setBook(book);
        detail.setPurchaseHistory(purchaseHistory);

        return purchaseHistoryRepository.save(purchaseHistory);
    }

    public PurchaseHistory updatePurchaseHistory(Long purchaseId, PurchaseHistory updatedPurchaseHistory) {
        User user = getAuthenticatedUser();

        PurchaseHistory existing = purchaseHistoryRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("Purchase history not found"));

        if (!existing.getCustomer().getUser().getUsername().equals(user.getUsername())) {
            throw new SecurityException("Unauthorized update attempt");
        }

        PurchaseDetail existingDetail = existing.getPurchaseDetails().iterator().next();
        Book oldBook = getBookById(existingDetail.getBook().getId());

        oldBook.setQuantity(oldBook.getQuantity() + existingDetail.getQuantity());
        bookRepository.save(oldBook);

        PurchaseDetail newDetail = updatedPurchaseHistory.getPurchaseDetails().iterator().next();
        Book newBook = getBookById(newDetail.getBook().getId());

        if (newBook.getQuantity() < newDetail.getQuantity()) {
            throw new IllegalArgumentException("Insufficient book quantity for update");
        }

        newBook.setQuantity(newBook.getQuantity() - newDetail.getQuantity());
        bookRepository.save(newBook);

        existingDetail.setBook(newBook);
        existingDetail.setQuantity(newDetail.getQuantity());
        existingDetail.setPrice(newBook.getPrice());

        return purchaseHistoryRepository.save(existing);
    }

    public void deletePurchaseHistory(Long purchaseId) {
        User user = getAuthenticatedUser();

        PurchaseHistory history = purchaseHistoryRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("Purchase history not found"));

        if (!history.getCustomer().getUser().getUsername().equals(user.getUsername())) {
            throw new SecurityException("Unauthorized delete attempt");
        }

        PurchaseDetail detail = history.getPurchaseDetails().iterator().next();
        Book book = getBookById(detail.getBook().getId());

        book.setQuantity(book.getQuantity() + detail.getQuantity());
        bookRepository.save(book);

        purchaseHistoryRepository.deleteById(purchaseId);
    }

    // ---------- Helper Methods ----------

    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
    }

    private Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + id));
    }
}
