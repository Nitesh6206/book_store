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
    private  final UserRepository userRepository;

    public CustomerService(PurchaseHistoryRepository purchaseHistoryRepository, BookRepository bookRepository,UserRepository userRepository) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.bookRepository = bookRepository;
        this.userRepository=userRepository;
    }

    public List<Book> getPurchasedBooks() {
        String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user=userRepository.findByUsername(authenticatedUser);
        if(user.isEmpty()){
            throw new RuntimeException("User Does Not exits");
        }
        User userData=user.get();

        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findByCustomerUser(userData);
        return purchaseHistories.stream()
                .flatMap(ph -> ph.getPurchaseDetails().stream())
                .map(PurchaseDetail::getBook)
                .distinct()
                .collect(Collectors.toList());
    }

    public PurchaseHistory createPurchasedOrder(PurchaseHistory purchaseHistory) {
        String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpt = userRepository.findByUsername(authenticatedUser);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();
        if (user.getCustomer() == null) {
            throw new IllegalArgumentException("CustomerDetails not found for user");
        }

        purchaseHistory.setCustomer(user.getCustomer());  // ✅ Set the persisted customer

        PurchaseDetail purchaseDetail = purchaseHistory.getPurchaseDetails().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Purchase details cannot be empty"));

        Book book=bookRepository.findById(purchaseDetail.getBook().getId())
                .orElseThrow(()->new RuntimeException("Book Not found"));

        if (book.getQuantity() < purchaseDetail.getQuantity()) {
            throw new IllegalArgumentException("Insufficient book quantity");
        }

        book.setQuantity(book.getQuantity() - purchaseDetail.getQuantity());
        bookRepository.save(book);

        if(purchaseDetail.getPrice()!= (book.getPrice()*purchaseDetail.getQuantity())){
            throw new RuntimeException("Purchased price are not matching with the number of book buy price");
        }

        purchaseDetail.setPurchaseHistory(purchaseHistory);
        purchaseDetail.setPrice(purchaseDetail.getPrice());

        return purchaseHistoryRepository.save(purchaseHistory);
    }


    public PurchaseHistory updatePurchaseHistory(Long purchaseId, PurchaseHistory updatedPurchaseHistory) {
        String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<PurchaseHistory> existingPurchaseOptional = purchaseHistoryRepository.findById(purchaseId);
        if (existingPurchaseOptional.isEmpty()) {
            throw new IllegalArgumentException("Purchase history with ID " + purchaseId + " not found");
        }

        PurchaseHistory existingPurchase = existingPurchaseOptional.get();
//        if (!existingPurchase.getCustomer().getId().equals(authenticatedUser)) {
//            throw new SecurityException("Unauthorized to update this purchase history");
//        }

        PurchaseDetail updatedDetail = updatedPurchaseHistory.getPurchaseDetails().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Purchase details cannot be empty"));

        Book updatedBook = updatedDetail.getBook();
        if (updatedBook == null || updatedBook.getId() == 0) {
            throw new IllegalArgumentException("Book must be specified");
        }

        Optional<Book> bookOptional = bookRepository.findById(updatedBook.getId());
        if (bookOptional.isEmpty()) {
            throw new IllegalArgumentException("Book with ID " + updatedBook.getId() + " not found");
        }

        Book book = bookOptional.get();
        PurchaseDetail existingDetail = existingPurchase.getPurchaseDetails().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Existing purchase details not found"));

        // Revert original quantity
        Book originalBook = existingDetail.getBook();
        Optional<Book> originalBookOptional = bookRepository.findById(originalBook.getId());
        if (originalBookOptional.isPresent()) {
            Book original = originalBookOptional.get();
            original.setQuantity(original.getQuantity() + existingDetail.getQuantity());
            bookRepository.save(original);
        }

        // Check and update with new quantity
        if (book.getQuantity() < updatedDetail.getQuantity()) {
            throw new IllegalArgumentException("Insufficient book quantity for update");
        }
        book.setQuantity(book.getQuantity() - updatedDetail.getQuantity());
        bookRepository.save(book);

        existingDetail.setQuantity(updatedDetail.getQuantity());
        existingDetail.setPrice(book.getPrice());
        existingDetail.setBook(book);

        return purchaseHistoryRepository.save(existingPurchase);
    }

    public void deletePurchaseHistory(Long purchaseId) {
        String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<PurchaseHistory> purchaseOptional = purchaseHistoryRepository.findById(purchaseId);
        if (purchaseOptional.isEmpty()) {
            throw new IllegalArgumentException("Purchase history with ID " + purchaseId + " not found");
        }

        PurchaseHistory purchaseHistory = purchaseOptional.get();
        if (!purchaseHistory.getCustomer().getUser().equals(authenticatedUser)) {
            throw new SecurityException("Unauthorized to delete this purchase history");
        }

        PurchaseDetail purchaseDetail = purchaseHistory.getPurchaseDetails().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Purchase details not found"));

        Book book = purchaseDetail.getBook();
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        if (bookOptional.isPresent()) {
            Book existingBook = bookOptional.get();
            existingBook.setQuantity(existingBook.getQuantity() + purchaseDetail.getQuantity());
            bookRepository.save(existingBook);
        }

        purchaseHistoryRepository.deleteById(purchaseId);
    }
}