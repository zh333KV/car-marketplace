package core.controller;
import core.entity.User;
import core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalUserAdvice {
    private final UserService userService;
    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return userService.findByEmail(authentication.getName()).orElse(null);
    }
}
