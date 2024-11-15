package com.kancth.navybucketstorage.domain.bucket.entity;

import com.kancth.navybucketstorage.domain.bucket.BucketAccessLevel;
import com.kancth.navybucketstorage.domain.bucket.dto.CreateBucketRequest;
import com.kancth.navybucketstorage.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buckets")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE buckets SET deleted_at = NOW() WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BucketAccessLevel accessLevel;
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static Bucket create(CreateBucketRequest request, User user) {
        return Bucket.builder()
                .owner(user)
                .name(request.name())
                .accessLevel(request.accessLevel())
                .description(request.description())
                .build();
    }
}
