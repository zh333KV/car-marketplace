package trib.dev.autosales.service;

import trib.dev.autosales.dto.AdCreateRequest;
import trib.dev.autosales.entity.Ad;
import trib.dev.autosales.entity.User;

import java.util.List;

public interface AdService {
    Ad createAd(AdCreateRequest request, User author);
    List<Ad> findAll();
    Ad findById(Long id);
    List<Ad> search(String query, String city);
    List<Ad> findByAuthorId(Long authorId);
    Ad updateAd(Long adId, AdCreateRequest request, User currentUser);
    void deleteAd(Long adId, User currentUser);
}
