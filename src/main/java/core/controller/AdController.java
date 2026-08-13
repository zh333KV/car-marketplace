package core.controller;
import core.dto.AdCreateRequest;
import core.entity.Ad;
import core.entity.User;
import core.service.AdService;
import core.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class AdController {
    private final AdService adService;
    private final UserService userService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("ads", adService.findAll());
        return "index";
    }
    @GetMapping("/ads")
    public String adsList(Model model) {
        model.addAttribute("ads", adService.findAll());
        return "ads/list";
    }
    @GetMapping("/ads/{id}")
    public String adDetails(@PathVariable Long id, Model model, Authentication authentication) {
        Ad ad = adService.findById(id);
        User currentUser = resolveCurrentUser(authentication);
        model.addAttribute("ad", ad);
        model.addAttribute("canEdit", adService.canUserEdit(ad, currentUser));
        return "ads/detail";
    }
    @GetMapping("/ads/create")
    public String createAdPage(Model model) {
        model.addAttribute("adCreateRequest", new AdCreateRequest());
        return "ads/create";
    }
    @PostMapping("/ads/create")
    public String createAd(
            @Valid AdCreateRequest adCreateRequest,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "ads/create";
        }
        User author = userService.getByEmail(authentication.getName());
        Ad ad = adService.createAd(adCreateRequest, author);
        return "redirect:/ads/" + ad.getId();
    }
    @GetMapping("/ads/edit/{id}")
    public String editAdPage(@PathVariable Long id, Authentication authentication, Model model) {
        User currentUser = userService.getByEmail(authentication.getName());
        model.addAttribute("adId", id);
        model.addAttribute("adCreateRequest", adService.getEditRequest(id, currentUser));
        return "ads/edit";
    }
    @PostMapping("/ads/edit/{id}")
    public String updateAd(
            @PathVariable Long id,
            @Valid @ModelAttribute("adCreateRequest") AdCreateRequest adCreateRequest,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("adId", id);
            return "ads/edit";
        }
        User currentUser = userService.getByEmail(authentication.getName());
        Ad updated = adService.updateAd(id, adCreateRequest, currentUser);
        return "redirect:/ads/" + updated.getId();
    }
    @PostMapping("/ads/delete/{id}")
    public String deleteAd(@PathVariable Long id, Authentication authentication) {
        User currentUser = userService.getByEmail(authentication.getName());
        adService.deleteAd(id, currentUser);
        return "redirect:/ads";
    }
    @GetMapping("/ads/search")
    public String search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String city,
            Model model
    ) {
        List<Ad> ads = adService.search(q, city);
        model.addAttribute("ads", ads);
        model.addAttribute("q", q);
        model.addAttribute("city", city);
        return "ads/list";
    }
    @GetMapping("/users/{id}")
    public String sellerPage(@PathVariable Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("seller", user);
        model.addAttribute("sellerAds", adService.findByAuthorId(id));
        return "users/profile";
    }

    private User resolveCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return userService.getByEmail(authentication.getName());
    }
}
