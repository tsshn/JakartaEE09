package com.yatsyshyn.JakartaEE09.controllers;

import com.yatsyshyn.JakartaEE09.entities.Book;
import com.yatsyshyn.JakartaEE09.entities.Permission;
import com.yatsyshyn.JakartaEE09.entities.User;
import com.yatsyshyn.JakartaEE09.entities.UserDetails;
import com.yatsyshyn.JakartaEE09.dto.UserDto;
import com.yatsyshyn.JakartaEE09.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("isFullyAuthenticated()")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize(value = "!isFullyAuthenticated()")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDto> create(@Valid @RequestBody final UserDto userModel) {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission(1, com.yatsyshyn.JakartaEE09.entities.type.Permission.VIEW_CATALOG));
        User user = userService.create(userModel.getLogin(), userModel.getPassword(), permissions);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getPassword(), user.getLogin()));
    }

    @GetMapping("/details")
    public ResponseEntity<UserDetails> getDetails() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userDetails);
    }

    @GetMapping(value = "/liked")
    public ResponseEntity<Set<Book>> getLiked() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.getLiked(userDetails.getUsername()));
    }

    @RequestMapping(value = "/liked/{bookId}", method = RequestMethod.POST)
    public ResponseEntity<Set<Book>> addLiked(@PathVariable String bookId) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = userService.addLiked(userDetails.getUsername(), Integer.parseInt(bookId));
        return ResponseEntity.ok(userEntity.getLiked());
    }

    @RequestMapping(value = "/liked/{bookId}", method = RequestMethod.DELETE)
    public ResponseEntity<Set<Book>> removeLiked(@PathVariable String bookId) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = userService.removeLiked(userDetails.getUsername(), Integer.parseInt(bookId));
        return ResponseEntity.ok(userEntity.getLiked());
    }
}