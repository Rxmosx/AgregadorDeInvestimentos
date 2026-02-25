package tech.buildrun.agregadordeinvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.buildrun.agregadordeinvestimentos.controller.CreateUserDto;
import tech.buildrun.agregadordeinvestimentos.entity.User;
import tech.buildrun.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService  userService;

    @Captor
    private ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);


    @Nested
    class createUser {


        @Test
        @DisplayName("Should create a user  with  success")
        void shouldCreateAUserWithSuccess ()  {

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(user).when(userRepository).save(argumentCaptor.capture());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "123"
            );


            var output = userService.createUser(input);

            assertEquals(input.username(), argumentCaptor.getValue().getUsername());
            assertEquals(input.email(), argumentCaptor.getValue().getEmail());
            assertEquals(input.password(), argumentCaptor.getValue().getPassword());

        }


        @Test
        void shouldTrhowExceptionWhenErrorOcurrs() {

            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "123"
            );

            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

}