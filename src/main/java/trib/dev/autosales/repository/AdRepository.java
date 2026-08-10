package trib.dev.autosales.repository;

import trib.dev.autosales.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByCityIgnoreCase(String city);
    List<Ad> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title,
            String description
    );
    List<Ad> findByCityIgnoreCaseAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String city,
            String title,
            String description
    );
    List<Ad> findByAuthorId(Long authorId);
}
