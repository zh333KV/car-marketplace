package core.controller;

import core.entity.User;
import core.service.AdService;
import core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final AdService adService;

    @GetMapping("/profile")
    public String myProfile(Authentication authentication, Model model) {
        User currentUser = userService.getByEmail(authentication.getName());
        model.addAttribute("user", currentUser);
        model.addAttribute("myAds", adService.findByAuthorId(currentUser.getId()));
        return "profile";
    }
}
