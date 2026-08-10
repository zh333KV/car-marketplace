package trib.dev.autosales.service.impl;

import trib.dev.autosales.dto.AdCreateRequest;
import trib.dev.autosales.entity.Ad;
import trib.dev.autosales.entity.AdImage;
import trib.dev.autosales.entity.User;
import trib.dev.autosales.repository.AdRepository;
import trib.dev.autosales.service.AdService;
import trib.dev.autosales.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final FileStorageService fileStorageService;
    @Override
    public Ad createAd(AdCreateRequest request, User author) {
        Ad ad = new Ad();
        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setPrice(request.getPrice());
        ad.setCity(request.getCity());
        ad.setAuthor(author);
        ad.setActive(true);
        saveImages(request, ad);
        return adRepository.save(ad);
    }
    @Override
    public List<Ad> findAll() {
        return sortByCreatedAtDesc(adRepository.findAll());
    }
    @Override
    public Ad findById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Объявление не найдено"));
    }
    @Override
    public List<Ad> search(String query, String city) {
        boolean hasQuery = query != null && !query.isBlank();
        boolean hasCity = city != null && !city.isBlank();
        if (hasQuery && hasCity) {
            return sortByCreatedAtDesc(adRepository.findByCityIgnoreCaseAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    city,
                    query,
                    query
            ));
        }
        if (hasCity) {
            return sortByCreatedAtDesc(adRepository.findByCityIgnoreCase(city));
        }
        if (hasQuery) {
            return sortByCreatedAtDesc(adRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query));
        }
        return sortByCreatedAtDesc(adRepository.findAll());
    }
    @Override
    public List<Ad> findByAuthorId(Long authorId) {
        return sortByCreatedAtDesc(adRepository.findByAuthorId(authorId));
    }
    @Override
    public Ad updateAd(Long adId, AdCreateRequest request, User currentUser) {
        Ad ad = findById(adId);
        checkOwnership(ad, currentUser);
        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setPrice(request.getPrice());
        ad.setCity(request.getCity());
        return adRepository.save(ad);
    }
    @Override
    public void deleteAd(Long adId, User currentUser) {
        Ad ad = findById(adId);
        checkOwnership(ad, currentUser);
        adRepository.delete(ad);
    }
    private void checkOwnership(Ad ad, User currentUser) {
        if (!ad.getAuthor().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("У вас нет прав на это объявление");
        }
    }
    private void saveImages(AdCreateRequest request, Ad ad) {
        MultipartFile[] images = request.getImages();

        if (images == null) {
            return;
        }
        for (MultipartFile file : images) {
            if (file == null || file.isEmpty()) {
                continue;
            }
            try {
                String savedFileName = fileStorageService.save(file);
                if (savedFileName == null) {
                    continue;
                }
                AdImage image = new AdImage();
                image.setFileName(savedFileName);
                image.setFilePath("/uploads/" + savedFileName);
                image.setAd(ad);
                ad.getImages().add(image);
            } catch (IOException e) {
                throw new RuntimeException("Не удалось сохранить файл: " + file.getOriginalFilename(), e);
            }
        }
    }

    private List<Ad> sortByCreatedAtDesc(List<Ad> ads) {
        return ads.stream()
                .sorted(Comparator.comparing(Ad::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }
}
