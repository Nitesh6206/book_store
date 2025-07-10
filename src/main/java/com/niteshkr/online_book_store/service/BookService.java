package com.niteshkr.online_book_store.service;

import com.niteshkr.online_book_store.Repository.BookRepository;
import com.niteshkr.online_book_store.entity.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Service class for managing book-related operations in the online bookstore.
 */
@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books from the repository.
     *
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        validateBook(book);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }

    public Book updateBook(Long id, Book book) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new RuntimeException("Book with ID " + id + " not found");
        }
        validateBook(book);
        Book existingBook = bookOptional.get();
        updateBookFields(existingBook, book);
        return bookRepository.save(existingBook);
    }

    private void validateBook(Book book) {
        if (book == null) {
            throw new RuntimeException("Book cannot be null");
        }
        if (book.getName() == null || book.getName().trim().isEmpty()) {
            throw new RuntimeException("Book name cannot be empty");
        }
        // Check if price is provided and valid
        if (book.getPrice() != null && book.getPrice() < 0) {
            throw new RuntimeException("Price cannot be negative");
        }
        // Check if quantity is provided and valid
        if (book.getQuantity() != null && book.getQuantity() < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
    }

    private void updateBookFields(Book existingBook, Book newBook) {
        if (newBook.getName() != null) {
            existingBook.setName(newBook.getName());
        }
        if (newBook.getBookDetail() != null) {
            existingBook.setBookDetail(newBook.getBookDetail());
        }
        if (newBook.getPrice() != null) {
            existingBook.setPrice(newBook.getPrice());
        }
        if (newBook.getQuantity() != null) {
            existingBook.setQuantity(newBook.getQuantity());
        }
    }
}