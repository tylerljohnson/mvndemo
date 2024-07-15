package acme.mvndemo.controller.api;

import acme.mvndemo.entity.*;
import acme.mvndemo.exception.*;
import acme.mvndemo.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/books")
public class BookApiController {
    private final BookService bookService;

    // Constructor
    public BookApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @DeleteMapping({"/", ""})
    public ResponseEntity<Void> deleteAll() {
        bookService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Book> create(@RequestBody BookDto dto) {
        Book book = Book.builder()
            .title(dto.getTitle())
            .author(dto.getAuthor())
            .isbn(dto.getIsbn())
            .build();
        return new ResponseEntity<>(bookService.create(book), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> queryByExample(@RequestParam String title, @RequestParam String author, @RequestParam String isbn) {
        Book book = Book.builder().title(title).author(author).isbn(isbn).build();
        return ResponseEntity.ok(bookService.queryByExample(book));
    }

    @GetMapping("/count")
    public ResponseEntity count() {
        return ResponseEntity.ok(bookService.count());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(bookService.getById(id).orElseThrow(() -> new NotFoundException(Book.class, id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateById(@PathVariable Long id, @RequestBody BookDto dto) throws NotFoundException {
        Book book = Book.builder()
            .title(dto.getTitle())
            .author(dto.getAuthor())
            .isbn(dto.getIsbn())
            .build();
        return ResponseEntity.ok(bookService.updateById(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
