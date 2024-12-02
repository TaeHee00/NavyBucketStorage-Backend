package com.kancth.navybucketstorage.domain.security.dto;

import com.kancth.navybucketstorage.domain.security.CredentialType;
import lombok.Builder;

@Builder
public record CredentialClaims(
        Long entityId,
        CredentialType credentialType
) {
}
