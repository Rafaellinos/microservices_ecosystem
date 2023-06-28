package com.example.user.application.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(Long id) {
        super("User not found exception for id " + id);
    }

    public UserNotFoundException(String cpfCnpj) {
        super("User not found exception for cpf/cnpj " + cpfCnpj);
    }
}
