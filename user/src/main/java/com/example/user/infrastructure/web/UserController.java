package com.example.user.infrastructure.web;

import com.example.user.application.exception.UserAlreadyExistsException;
import com.example.user.application.exception.UserNotFoundException;
import com.example.user.application.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/{cpfCnpj}")
    public UserDto getUserByCpfCnpj(
            @PathVariable(name = "cpfCnpj") String cpfCnpj
    ) {
        try {
            return this.userService.getUserByCpfCnpj(cpfCnpj);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(
        @RequestBody UserRequestDto userRequestDto
    ) {
        try {
            return this.userService.createUser(
                    new ModelMapper().map(userRequestDto, UserDto.class)
            );
        } catch (UserAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage()
            );
        }
    }

    @PutMapping
    public UserDto updateUser(
            @RequestBody UserRequestDto userRequestDto
    ) {
        try {
            return this.userService.updateUserById(
                    new ModelMapper().map(userRequestDto, UserDto.class)
            );
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage()
            );
        }
    }
}
