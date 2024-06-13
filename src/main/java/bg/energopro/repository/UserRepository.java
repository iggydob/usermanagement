package bg.energopro.repository;

import bg.energopro.domain.User;
import bg.energopro.form.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public interface UserRepository extends PanacheRepository<User> {

    User createUser(User user);

    User getUserByEmail(String email);

    User getUserById(Long userId);

    User deleteUser(User user);

    User updateUserDetails(UpdateForm user);

    List<User> filterUsers(FilterForm filterForm);
}
