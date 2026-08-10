package trib.dev.autosales.service;

import trib.dev.autosales.dto.RegisterRequest;
import trib.dev.autosales.entity.User;

public interface AuthService {
    User register(RegisterRequest request);
}
