package bg.energopro.service;

import bg.energopro.domain.User;
import bg.energopro.dto.UserDto;
import bg.energopro.form.*;

import java.util.List;

public interface UserService {

    UserDto createUser(User user);

    UserDto getUserByEmail(String email);

    UserDto deleteUser(UserDto user);

    UserDto updateUserDetails(UpdateForm user);

    List<User> filterUsers(FilterForm filterForm);
}
