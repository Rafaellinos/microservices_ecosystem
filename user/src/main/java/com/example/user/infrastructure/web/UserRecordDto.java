package com.example.user.infrastructure.web;

import jakarta.validation.constraints.NotNull;

// extends records by default, do not allow multi inheritance
// class already static
public record UserRecordDto(
        @NotNull String name,
        Long id,
        @NotNull String cpfCnpj
) {
    // only static fields

    public void printNome() {
        System.out.println(name + " " + cpfCnpj);
    }

}
