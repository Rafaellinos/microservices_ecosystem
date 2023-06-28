package com.example.user.infrastructure.web;

import org.springframework.stereotype.Component;

@Component
public class UserRequestDto {

    private String name;
    private String cpfCnpj;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
}
