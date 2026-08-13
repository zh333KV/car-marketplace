package core.service;

import core.dto.AdCreateRequest;
import core.entity.Ad;
import core.entity.User;

import java.util.List;

public interface AdService {
    Ad createAd(AdCreateRequest request, User author);
    List<Ad> findAll();
    Ad findById(Long id);
    List<Ad> search(String query, String city);
    List<Ad> findByAuthorId(Long authorId);
    Ad updateAd(Long adId, AdCreateRequest request, User currentUser);
    void deleteAd(Long adId, User currentUser);
    boolean canUserEdit(Ad ad, User currentUser);
    AdCreateRequest getEditRequest(Long adId, User currentUser);
}
