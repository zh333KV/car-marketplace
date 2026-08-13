package core.service;

import core.dto.RegisterRequest;
import core.entity.User;

public interface AuthService {
    User register(RegisterRequest request);
}
