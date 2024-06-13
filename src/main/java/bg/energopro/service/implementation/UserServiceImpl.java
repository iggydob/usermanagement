package bg.energopro.service.implementation;

import bg.energopro.domain.User;
import bg.energopro.dto.UserDto;
import bg.energopro.form.*;
import bg.energopro.mapper.UserDtoMapper;
import bg.energopro.repository.UserRepository;
import bg.energopro.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Inject
    UserDtoMapper userDtoMapper;

    @Inject
    UserRepository userRepository;

    @Override
    public UserDto createUser(User user) {
        return userDtoMapper.mapToUserDto(userRepository.createUser(user));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userDtoMapper.mapToUserDto(userRepository.getUserByEmail(email));
    }

    @Override
    public UserDto deleteUser(UserDto user) {
        return userDtoMapper.mapToUserDto(userRepository.deleteUser(userDtoMapper.mapToUser(user)));
    }

    @Override
    public UserDto updateUserDetails(UpdateForm user) {
        return userDtoMapper.mapToUserDto(userRepository.updateUserDetails(user));
    }

    @Override
    public List<User> filterUsers(FilterForm filterForm) {
        return userRepository.filterUsers(filterForm);
    }
}
