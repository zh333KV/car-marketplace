package core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ad_images")
@Getter
@Setter
public class AdImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private String filePath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;
}
