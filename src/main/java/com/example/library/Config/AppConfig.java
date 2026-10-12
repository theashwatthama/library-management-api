package com.example.library.Config;

import com.example.library.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditor")
public class AppConfig {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    AuditorAware<String> getAuditor(){
        return new AuditorAwareImpl();
    }

}
