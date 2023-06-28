package com.example.user.application.exception;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String cpfCnpj) {
        super("User with cpf/cnpj " + cpfCnpj + " already exists");
    }
}
