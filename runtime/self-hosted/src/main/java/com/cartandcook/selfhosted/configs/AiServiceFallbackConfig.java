package com.cartandcook.selfhosted.configs;

import com.cartandcook.core.api.AiService;
import com.cartandcook.core.api.DisabledAiService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiServiceFallbackConfig {

    @Bean
    @ConditionalOnMissingBean(AiService.class)
    public AiService disabledAiService() {
        return new DisabledAiService();
    }
}

