package trib.dev.autosales.controller;

import trib.dev.autosales.dto.AdCreateRequest;
import trib.dev.autosales.entity.Ad;
import trib.dev.autosales.entity.User;
import trib.dev.autosales.repository.UserRepository;
import trib.dev.autosales.service.AdService;
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
    private final UserRepository userRepository;

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
        model.addAttribute("ad", ad);
        boolean canEdit = false;
        if (authentication != null) {
            User currentUser = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
            canEdit = ad.getAuthor().getId().equals(currentUser.getId());
        }
        model.addAttribute("canEdit", canEdit);
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
        User author = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        Ad ad = adService.createAd(adCreateRequest, author);
        return "redirect:/ads/" + ad.getId();
    }
    @GetMapping("/ads/edit/{id}")
    public String editAdPage(@PathVariable Long id, Authentication authentication, Model model) {
        Ad ad = adService.findById(id);
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        if (!ad.getAuthor().getId().equals(currentUser.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("Нет доступа");
        }
        AdCreateRequest dto = new AdCreateRequest();
        dto.setTitle(ad.getTitle());
        dto.setDescription(ad.getDescription());
        dto.setPrice(ad.getPrice());
        dto.setCity(ad.getCity());
        model.addAttribute("adId", id);
        model.addAttribute("adCreateRequest", dto);
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
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        Ad updated = adService.updateAd(id, adCreateRequest, currentUser);
        return "redirect:/ads/" + updated.getId();
    }

    @PostMapping("/ads/delete/{id}")
    public String deleteAd(@PathVariable Long id, Authentication authentication) {
        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        model.addAttribute("seller", user);
        model.addAttribute("sellerAds", adService.findByAuthorId(id));
        return "users/profile";
    }
}
