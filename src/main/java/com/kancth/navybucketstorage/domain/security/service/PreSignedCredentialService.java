package com.kancth.navybucketstorage.domain.security.service;

import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.bucket.exception.BucketNotFoundException;
import com.kancth.navybucketstorage.domain.bucket.repository.BucketRepository;
import com.kancth.navybucketstorage.domain.file.entity.File;
import com.kancth.navybucketstorage.domain.file.exception.FileNotFoundException;
import com.kancth.navybucketstorage.domain.file.repository.FileRepository;
import com.kancth.navybucketstorage.domain.security.CredentialType;
import com.kancth.navybucketstorage.domain.security.dto.CredentialClaims;
import com.kancth.navybucketstorage.domain.security.entity.PreSignedCredential;
import com.kancth.navybucketstorage.domain.security.exception.ExpiredTokenException;
import com.kancth.navybucketstorage.domain.security.exception.InvalidTokenException;
import com.kancth.navybucketstorage.domain.security.repository.PreSignedCredentialRepository;
import com.kancth.navybucketstorage.domain.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class PreSignedCredentialService {
    private final PasswordEncoder passwordEncoder;
    private final PreSignedCredentialRepository preSignedCredentialRepository;
    private final BucketRepository bucketRepository;
    private final FileRepository fileRepository;

    public Jws<Claims> parseClaims(String credential) {
        Jws<Claims> claimsJws;
        try {
            PreSignedCredential preSignedCredential = preSignedCredentialRepository.findByCredential(credential)
                    .orElseThrow(InvalidTokenException::new);
            // FIXME: 이거 PasswordEncoder로 암호화하면 같은값이여도 다른 값 나올꺼같은데
            byte[] keyBytes = Decoders.BASE64.decode(preSignedCredential.getSigningKeyBytes());
            SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);

            claimsJws = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(credential);
        } catch (ExpiredJwtException ex) {
            // Exception 새로 하나 만들기
            throw new ExpiredTokenException();
        } catch (JwtException ex) {
            // Exception 새로 하나 만들기
            throw new InvalidTokenException();
        }
        return claimsJws;
    }

    // TODO: Credential Type 확인 후 메서드 하나로 처리 (Bucket, Object)
    public String genUploadPreSignedCredential(User bucketOwner, Bucket bucket, Long expiredSeconds) {
        Date expiration = new Date(System.currentTimeMillis() + (expiredSeconds * 1000));

        String signingKeyBytes = passwordEncoder.encode(bucketOwner.getEmail());
        byte[] keyBytes = Decoders.BASE64.decode(signingKeyBytes);
        SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);

        String credential = Jwts.builder()
                .subject(Long.toString(bucket.getId()))
                .claim("entityId", bucket.getId())
                .claim("credentialType", "BUCKET")
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
        preSignedCredentialRepository.save(PreSignedCredential.of(credential, signingKeyBytes));
        return credential;
    }

    public <T> T getEntityCredential(String credential, String entityName, Class<T> entityClass) {
        // bucket -> navyBucket.4?credential=ljlsdjkasdlj
        // object -> navyBucket.4/logo.png?credential=dfsjklsdf
        CredentialType credentialType;
        T response = null;
        CredentialClaims claims = this.getClaims(credential);

        switch (claims.credentialType()) {
            case BUCKET -> {
                if (!entityClass.isInstance(Bucket.class)) {
                    throw new InvalidTokenException();
                }

                String[] splitBucketName = entityName.split("\\.");
                String orgBucketName = splitBucketName[0];
                Long ownerId = Long.parseLong(splitBucketName[1]);

                response = (T) bucketRepository.findByNameAndOwner_Id(orgBucketName, ownerId).orElseThrow(BucketNotFoundException::new);
            }
            case OBJECT -> {
                if (!entityClass.isInstance(File.class)) {
                    throw new InvalidTokenException();
                }

                String[] splitUrl = entityName.split("/");
                String bucketName = splitUrl[0];
                String objectName = splitUrl[1];

                String[] splitBucketName = bucketName.split("\\.");
                String orgBucketName = splitBucketName[0];
                Long ownerId = Long.parseLong(splitBucketName[1]);

                response = (T) fileRepository.findByFileNameAndBucket_NameAndBucket_Owner_Id(objectName, orgBucketName, ownerId).orElseThrow(FileNotFoundException::new);
            }
        }
        return response;
    }

    private CredentialClaims getClaims(String credential) {
        Jws<Claims> claims = this.parseClaims(credential);
        CredentialType credentialType = CredentialType.valueOf(claims.getPayload().get("credentialType", String.class));

        return CredentialClaims.builder()
                .entityId(claims.getPayload().get("entityId", Long.class))
                .credentialType(credentialType)
                .build();
    }

}
