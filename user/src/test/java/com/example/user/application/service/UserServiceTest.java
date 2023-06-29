package com.example.user.application.service;

import com.example.user.application.UserRepository;
import com.example.user.domain.User;
import com.example.user.infrastructure.web.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Create a mock UserRepository
        userRepository = mock(UserRepository.class);

        // Instantiate the UserService with the mock UserRepository
        userService = new UserService(userRepository);
    }

    @Test
    void getAllUsers_ReturnsListOfUserDto() {

        // Arrange
        User user1 = new User("John", 1L);
        User user2 = new User("Jane", 2L);
        List<User> userList = Arrays.asList(user1, user2);

        // Stub
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserDto> userDtoList = userService.getAllUsers();

        // Assert
        verify(userRepository, times(1)).findAll();
        assertNotNull(userDtoList);
        assertEquals(2, userDtoList.size());
        assertEquals(user1.getId(), userDtoList.get(0).getId());
        assertEquals(user1.getName(), userDtoList.get(0).getName());
        assertEquals(user1.getCpfCnpj(), userDtoList.get(0).getCpfCnpj());
        assertEquals(user2.getId(), userDtoList.get(1).getId());
        assertEquals(user2.getName(), userDtoList.get(1).getName());
        assertEquals(user2.getCpfCnpj(), userDtoList.get(1).getCpfCnpj());
    }
}
