package com.example.user.application;

import com.example.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByCpfCnpj(String cpfCnpj);

    List<User> findAll();

    void deleteById(Long id);

}
