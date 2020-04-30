package com.yatsyshyn.JakartaEE09.controllers;

import com.yatsyshyn.JakartaEE09.services.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final BookService bookService;

    public PageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/cabinet")
    public String profile() {
        return "cabinet";
    }

    @GetMapping("/library")
    public String bookCatalog() {
        return "library";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
