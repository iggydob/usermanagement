package bg.energopro.repository.implementation;

import bg.energopro.domain.User;
import bg.energopro.exceptions.ApiException;
import bg.energopro.exceptions.*;
import bg.energopro.form.FilterForm;
import bg.energopro.form.UpdateForm;
import bg.energopro.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository, PanacheRepository<User> {

    @Inject
    SessionFactory sessionFactory;

    @Override
    @Transactional
    public User createUser(User user) {
        if (getUserByEmail(user.getEmail()) != null) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }
        persist(user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            throw new EntityNotFoundException("User", "email", email);
        }
        return find("email", email).firstResult();
    }

    @Override
    public User getUserById(Long userId) {
        if (userId == null) {
            throw new EntityNotFoundException("User", "user id", userId.toString());
        }
        return find("userId", userId).firstResult();
    }

    @Override
    @Transactional
    public User deleteUser(User user) {
        if (getUserById(user.getUserId()) == null) {
            throw new EntityNotFoundException("User", "user id", user.getUserId().toString());
        }
        User userToDelete = getUserById(user.userId);
        delete(user);
        return userToDelete;
    }

    @Override
    @Transactional
    public User updateUserDetails(UpdateForm userForm) {
        if (getUserById(userForm.getUserId()) == null) {
            throw new EntityNotFoundException("User", "user id", userForm.getUserId().toString());
        }
        int updatedCount = update("firstName = ?1, lastName = ?2, email = ?3, address = ?4, phone = ?5, bio = ?6 where userId = ?7",
                userForm.getFirstName(), userForm.getLastName(), userForm.getEmail(), userForm.getAddress(), userForm.getPhone(), userForm.getBio(), userForm.getUserId());
        if (updatedCount == 0) {
            throw new ApiException("Something went wrong. Please try again.");
        }
        return getUserById(userForm.getUserId());
    }

    @Override
    public List<User> filterUsers(FilterForm filterForm) {
        try (Session session = sessionFactory.openSession()) {
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterForm.getFirstName().ifPresent(value -> {
                filters.add("firstName ILIKE :firstName");
                params.put("firstName", String.format("%%%s%%", filterForm.getFirstName()));
            });

            filterForm.getLastName().ifPresent(value -> {
                filters.add("lastName ILIKE :lastName");
                params.put("lastName", String.format("%%%s%%", filterForm.getLastName()));
            });

            filterForm.getEmail().ifPresent(value -> {
                filters.add("email LIKE :email");
                params.put("email", String.format("%%%s%%", filterForm.getEmail()));
            });

            StringBuilder queryString = new StringBuilder("FROM User");
            if (!filters.isEmpty()) {
                queryString.append(" WHERE ").append(String.join(" AND ", filters));
            }

            Query<User> query = session.createQuery(queryString.toString(), User.class);
            query.setProperties(params);
            return query.list();
        }
    }
}

