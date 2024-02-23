package org.openpsn.api.service;

import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.SecureRandom;

@Singleton
public class SecurityService {
    private final SecureRandom secureRandom;

    @Inject
    @SneakyThrows
    public SecurityService() {
        secureRandom = SecureRandom.getInstanceStrong();
    }

    public int randomInt() {
        return secureRandom.nextInt();
    }
}
