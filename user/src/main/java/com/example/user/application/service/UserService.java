package com.example.user.application.service;

import com.example.user.application.UserRepository;
import com.example.user.application.exception.UserAlreadyExistsException;
import com.example.user.application.exception.UserNotFoundException;
import com.example.user.domain.User;
import com.example.user.infrastructure.web.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        List<User> userList = this.userRepository.findAll();
        return new ModelMapper().map(
                userList, new TypeToken<List<UserDto>>() {}.getType()
        );
    }

    private User getUserById(Long id) throws UserNotFoundException {
        return this.userRepository.findById(
                id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserDto getUserByCpfCnpj(String cpfCnpj) throws UserNotFoundException {
        User user = this.userRepository.findByCpfCnpj(
                cpfCnpj).orElseThrow(() -> new UserNotFoundException(cpfCnpj));
        return new ModelMapper().map(user, UserDto.class);
    }

    public UserDto createUser(UserDto userDto) throws UserAlreadyExistsException {
        User user = new ModelMapper().map(userDto, User.class);
        user = this.userRepository.save(user);
        userDto.setId(user.getId());
        return userDto;
    }

    public UserDto deleteUserById(Long id) throws UserNotFoundException {
        User user = this.getUserById(id);
        this.userRepository.deleteById(user.getId());
        return new ModelMapper().map(user, UserDto.class);
    }

    public UserDto updateUserById(UserDto userDto) throws UserNotFoundException {
        User user = this.getUserById(userDto.getId());
        user.setName(userDto.getName());
        user.setCpfCnpj(userDto.getCpfCnpj());
        return new ModelMapper().map(user, UserDto.class);
    }

}
