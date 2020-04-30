package com.yatsyshyn.JakartaEE09.controllers;

import com.yatsyshyn.JakartaEE09.entities.Book;
import com.yatsyshyn.JakartaEE09.dto.BookDto;
import com.yatsyshyn.JakartaEE09.dto.FilterDto;
import com.yatsyshyn.JakartaEE09.services.BookService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseBody
    @GetMapping(value = "/get")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @PreAuthorize("hasAuthority('VIEW_ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<BookDto> bookFormControllerPost(@Valid @RequestBody final BookDto book) {
        Book bookEntity = bookService.add(book.getTitle(), book.getIsbn(), book.getAuthor());
        return ResponseEntity.ok(new BookDto(bookEntity.getTitle(), bookEntity.getAuthor(), bookEntity.getIsbn()));
    }

    @RequestMapping(value = "/get/{bookId}")
    public ResponseEntity<Book> getById(@PathVariable("bookId") Integer bookId) {
        return ResponseEntity.ok(bookService.getById(bookId));
    }

    @RequestMapping(value = "/filter", method = {RequestMethod.POST})
    public ResponseEntity<List<Book>> filter(@RequestBody final FilterDto filterDto) {
        return ResponseEntity.ok(bookService.filter(filterDto.getProperty(), filterDto.getInput()));
    }
}
