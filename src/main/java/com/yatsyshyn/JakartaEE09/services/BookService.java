package com.yatsyshyn.JakartaEE09.services;

import com.yatsyshyn.JakartaEE09.entities.Book;
import com.yatsyshyn.JakartaEE09.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book add(String title, String isbn, String author) {
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle(title);
        book.setIsbn(isbn);
        return bookRepository.saveAndFlush(book);
    }

    @Transactional
    public Book getById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public List<Book> getByTitle(String title) {
        return bookRepository.getByTitle('%' + title + '%');
    }

    @Transactional
    public List<Book> getByAuthor(String author) {
        return bookRepository.getByAuthor('%' + author + '%');
    }

    @Transactional
    public List<Book> getByIsbn(String isbn) {
        return bookRepository.getByIsbn('%' + isbn + '%');
    }

    @Transactional
    public List<Book> filter(String criteria, String searchWord) {
        System.out.println(getByTitle(searchWord));
        return switch (criteria) {
            case "title" -> getByTitle(searchWord);
            case "author" -> getByAuthor(searchWord);
            case "isbn" -> getByIsbn(searchWord);
            default -> getAll();
        };
    }
}
