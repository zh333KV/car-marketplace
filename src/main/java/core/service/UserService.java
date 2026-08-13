package core.service;

import core.entity.User;

import java.util.Optional;

public interface UserService {
    User getByEmail(String email);
    User getById(Long id);
    Optional<User> findByEmail(String email);
}
