package acme.mvndemo.service;

import acme.mvndemo.entity.*;
import acme.mvndemo.exception.*;
import acme.mvndemo.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class BookService {
    private final BookRepository bookRepository;

    // Constructor
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public long count() {
        return bookRepository.count();
    }

    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book updateById(Long id, Book book) throws NotFoundException {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new NotFoundException(Book.class, id));
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        return bookRepository.save(existingBook);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public List<Book> queryByExample(Book book) {
        Example<Book> example = Example.of(book);
        return bookRepository.findAll(example);
    }
}
