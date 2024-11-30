package com.kancth.navybucketstorage.domain.file.entity;

import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE files SET deleted_at = NOW() WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bucket_id", nullable = false)
    private Bucket bucket;

    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private long size;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private String url;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static File create(Bucket bucket, MultipartFile file) {
        boolean isContainsWhiteSpace = file.getOriginalFilename().contains(" ");
        String replaceFileName = file.getOriginalFilename().replaceAll(" ", "+");

        String saveUrl = bucket.getName() + "." + bucket.getOwner().getId() + "/" + file.getOriginalFilename();
        if (isContainsWhiteSpace) {
            saveUrl = bucket.getName() + "." + bucket.getOwner().getId() + "/" + replaceFileName;
        }
        return File.builder()
                .bucket(bucket)
                .fileName(file.getOriginalFilename())
                .size(file.getSize()) // Byte
                .path(bucket.getOwner().getId().toString() + "/" + bucket.getId().toString() + "/")
                .url(saveUrl)
                .build();
    }
}
