package bg.energopro.service.implementation;


import bg.energopro.domain.User;
import bg.energopro.dto.UserDto;
import bg.energopro.exceptions.EntityDuplicateException;
import bg.energopro.form.UpdateForm;
import bg.energopro.mapper.UserDtoMapper;
import bg.energopro.repository.implementation.UserRepositoryImpl;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class UserServiceImplTest {

    @InjectMock
    UserRepositoryImpl mockUserRepository;

    @Inject
    UserServiceImpl mockUserService;

    @Inject
    UserDtoMapper userDtoMapper;

    @Test
    void getUserByEmail_Should_getUserDtoWithMatchingEmail() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        Mockito.when(mockUserRepository.getUserByEmail(email)).thenReturn(user);

        // Act
        User result = userDtoMapper.mapToUser(mockUserService.getUserByEmail(email));

        // Assert
        assertEquals(email, result.getEmail());
        Mockito.verify(mockUserRepository).getUserByEmail(email);
    }

    @Test
    void createUser_Should_ThrowEntityDuplicateException_When_UserWithSameEmailExists() {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail("example@mock.com");

        // Act
        User newUser = new User();
        newUser.setUserId(1L);
        existingUser.setEmail("example@mock.com");

        // Assert
        Mockito.when(mockUserService.createUser(newUser))
                .thenThrow(new EntityDuplicateException("User", "e-mail", existingUser.getEmail()));

    }

    @Test
    void updateUserDetails_ShouldCallRepository_when_userIsUpdated() {
        // Arrange
//        UserDto userDto = userDtoMapper.mapToUserDto(existingUser);
        User existingUser = new User();
        existingUser.setUserId(1L);
//        existingUser.setFirstName("firstName");
//        existingUser.setLastName("lastName");
        existingUser.setEmail("example@mock.com");
//        existingUser.setAddress("San Francisco, CA, USA");
//        existingUser.setBio("Junior Java Developer");
//        existingUser.setCreatedAt(LocalDateTime.parse("2020-07-03T17:54:07"));

        UpdateForm updateForm = new UpdateForm();
        updateForm.setPhone("0888123456");

        // Act
        mockUserService.updateUserDetails(updateForm);

        // Assert
        Mockito.verify(mockUserRepository, Mockito.times(1)).updateUserDetails(updateForm);
    }

    @Test
    void deleteUser_ShouldCallRepository_when_userIsDeleted(){
        // Arrange
        User existingUser = new User();
        existingUser.setUserId(1L);

        // Act
        mockUserService.deleteUser(userDtoMapper.mapToUserDto(existingUser));

        // Assert
        Mockito.verify(mockUserRepository, Mockito.times(1)).deleteUser(Mockito.any(User.class));
    }
}
