package com.yoogesh.security.principalExtractor;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

public interface LemonPrincipalExtractor extends PrincipalExtractor {
	
    String DEFAULT = "default";
    String AUTHORITIES = "lemon-authorities";

    String getProvider();
}
