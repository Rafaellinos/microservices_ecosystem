package com.example.user.application.service;

import com.example.user.application.UserRepository;
import com.example.user.application.exception.UserNotFoundException;
import com.example.user.domain.User;
import com.example.user.infrastructure.web.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("User Service Tests")
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


    private static Stream<Arguments> inputListOfUsers() {
        return Stream.of(
           Arguments.of("Rafael", 3L, "John", 1L),
           Arguments.of("Yasmin", 5L, "Keitlin", 33L),
           Arguments.of("Jonh", 5L, "Jefferson", 88L)
        );
    }

    @ParameterizedTest
    @MethodSource("inputListOfUsers")
    @DisplayName("Service findAll list of users")
    void getAllUsers_ReturnsListOfUserDto(String userName1, long userId1, String userName2, long userId2) {

        // Arrange
        User user1 = new User(userName1, userId1);
        User user2 = new User(userName2, userId2);
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

    @DisplayName("No User found throw UserNotFoundException")
    @Test
    // good practice for naming:
    // test <System for testing>_<Condition>_<Expected>
    void testGetUserById_WhenNoUser_ShouldThrowUserNotFoundException() {

        // Arrange // Given
        Optional<User> emptyUser = Optional.empty();
        long userId = Mockito.anyLong();

        // Stub
        when(userRepository.findById(userId)).thenReturn(emptyUser);

        // Act and assert // When
        Exception exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(userId),
                "UserNotFound should have been thrown"
        );

        // Assert // Then
        assertEquals(
                (new UserNotFoundException(userId)).getMessage(),
                exception.getMessage(),
                () -> "Message of ID 1 is expected" // good practice
        );

    }
}
