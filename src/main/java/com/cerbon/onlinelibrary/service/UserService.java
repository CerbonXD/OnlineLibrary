package com.cerbon.onlinelibrary.service;

import com.cerbon.onlinelibrary.model.User;
import com.cerbon.onlinelibrary.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User create(@Valid User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, @Valid User updated) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setEmail(updated.getEmail());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }
}
