package com.yatsyshyn.JakartaEE09.services;

import com.yatsyshyn.JakartaEE09.entities.Book;
import com.yatsyshyn.JakartaEE09.entities.Permission;
import com.yatsyshyn.JakartaEE09.entities.User;
import com.yatsyshyn.JakartaEE09.repositories.BookRepository;
import com.yatsyshyn.JakartaEE09.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public User create(final String username, final String password, final List<Permission> permissions) {
        final User user = new User();
        user.setLogin(username);
        user.setPassword(password);
        user.setLiked(new HashSet<>());
        user.setPermissions(permissions);
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public Set<Book> getLiked(final String username) throws UsernameNotFoundException {
        final User user = userRepository.get(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user with login \"" + username + "\" was not found"));
        return user.getLiked();
    }

    @Transactional
    public User addLiked(final String username, final int book_id) throws UsernameNotFoundException {
        final User user = userRepository.get(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user with login \"" + username + "\" was not found"));
        final Book book = bookRepository.findById(book_id).orElseThrow(() -> new EntityNotFoundException("The book with id " + book_id + " was not found"));
        Set<Book> books = new HashSet<>(user.getLiked());
        books.add(book);
        user.setLiked(books);
        userRepository.saveAndFlush(user);
        return user;
    }

    @Transactional
    public User removeLiked(final String username, final int bookId) throws UsernameNotFoundException {
        final User user = userRepository.get(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user with login \"" + username + "\" was not found"));
        user.setLiked(user.getLiked().stream().filter(book -> book.getId() != bookId).collect(Collectors.toSet()));
        userRepository.saveAndFlush(user);
        return user;
    }
}
