package com.filk.config;

import com.filk.service.impl.DefaultSecurityService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class TestConfig {
    @Bean
    DefaultSecurityService securityService() {
        return Mockito.mock(DefaultSecurityService.class);
    }
}
