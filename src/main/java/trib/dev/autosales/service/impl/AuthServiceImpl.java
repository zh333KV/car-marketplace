package trib.dev.autosales.service.impl;

import trib.dev.autosales.dto.RegisterRequest;
import trib.dev.autosales.entity.Role;
import trib.dev.autosales.entity.User;
import trib.dev.autosales.repository.UserRepository;
import trib.dev.autosales.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setPhone(request.getPhone());
        user.setCity(request.getCity());
        user.setRole(Role.USER);
        user.setEnabled(true);
        return userRepository.save(user);
    }
}
