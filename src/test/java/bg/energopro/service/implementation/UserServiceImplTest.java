package bg.energopro.service.implementation;

import bg.energopro.domain.User;
import bg.energopro.dto.UserDto;
import bg.energopro.exceptions.EntityNotFoundException;
import bg.energopro.form.UpdateForm;
import bg.energopro.mapper.UserDtoMapper;
import bg.energopro.repository.implementation.UserRepositoryImpl;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@QuarkusTest
public class UserServiceImplTest {

    @InjectMock
    UserServiceImpl mockService;

    @InjectMock
    UserRepositoryImpl mockRepository;

    @Inject
    UserDtoMapper userDtoMapper;

    private static User createMockUser() {
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setEmail("john.doe@example.com");
        mockUser.setAddress("555-1234");
        mockUser.setBio("Loves hiking and outdoor activities.");
        mockUser.setCreatedAt(LocalDateTime.now());
        return mockUser;
    }

//    private static UserDto createUserDto() {
//        UserDto mockUserDto = new UserDto();
//        mockUserDto.setFirstName("John");
//        mockUserDto.setLastName("Doe");
//
//    }

    @Test
    void when_getUserById_then_userShouldBeFound() {
        // Arrange
        User sourceUser = createMockUser();
        User targetUser = createMockUser();

        // Act
        when(mockRepository.getUserById(1L)).thenReturn(targetUser);

        // Act, Assert
        assertNotNull(targetUser);
        assertEquals(targetUser, sourceUser);
    }

    @Test
    void createUser_Should_ThrowEntityDuplicateException_When_UserWithSameEmailExists() {
        // Arrange
        User existingUser = createMockUser();

        // Act, Assert
        when(mockService.createUser(createMockUser()))
                .thenThrow(new EntityNotFoundException("User", "e-mail", existingUser.getEmail()));
    }

    @Test
    void createUser_Should_callRepository_When() {
        // Arrange
        User newUser = createMockUser();

        // Act
        mockService.createUser(newUser);

        // Assert
        verify(mockRepository, times(1)).createUser(newUser);
    }

    @Test
    void update_Should_UpdateUserDetails_When_UserDetailAreNotNull() {
        // Arrange
        User actingUser = createMockUser();
        User dummyUser = createMockUser();
        dummyUser.setUserId(2L);
        dummyUser.setFirstName("Jane");

        UpdateForm updateForm = new UpdateForm();
        updateForm.setUserId(1L);
        updateForm.setFirstName("Jane");

        // Act
        mockService.updateUserDetails(updateForm);

        // Assert
        assertEquals(actingUser.getFirstName(), dummyUser.getFirstName());
        verify(mockRepository, times(1)).updateUserDetails(updateForm);
    }

    @Test
    void deleteUser_ShouldCallRepository_When_UserIsDeleted() {
        // Act
        User mockUser = createMockUser();

        // Act
        mockService.deleteUser(userDtoMapper.mapToUserDto(mockUser));

        // Assert
        verify(mockRepository, times(1)).deleteUser(mockUser);
    }
}
