package com.kancth.navybucketstorage.domain.security.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@RedisHash(value = "accessObject")
public class PreSignedCredential {

    @Id
    private String credential;
    private String signingKeyBytes;

    public static PreSignedCredential of(String credential, String signingKeyBytes) {
        return PreSignedCredential.builder()
                            .credential(credential)
                            .signingKeyBytes(signingKeyBytes)
                            .build();
    }
}
