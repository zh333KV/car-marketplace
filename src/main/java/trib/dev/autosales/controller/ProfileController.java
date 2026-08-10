package trib.dev.autosales.controller;

import trib.dev.autosales.entity.User;
import trib.dev.autosales.repository.UserRepository;
import trib.dev.autosales.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserRepository userRepository;
    private final AdService adService;

    @GetMapping("/profile")
    public String myProfile(Authentication authentication, Model model) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        model.addAttribute("user", currentUser);
        model.addAttribute("myAds", adService.findByAuthorId(currentUser.getId()));
        return "profile";
    }
}
