package dev.hspl.hspl2shop.common;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

@Configuration(proxyBeanMethods = false)
@EnableAsync
public class MasterConfig {
    @Bean
    public SecureRandom applicationSecureRandom() {
        try {
            return SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException exception) {
            return new SecureRandom();
        }
    }

    @Bean
    @Primary
    public MessageSource applicationMessageSource() {
        var result = new ResourceBundleMessageSource();
        result.setBasenames("messages");
        result.setDefaultEncoding("UTF-8");
        result.setDefaultLocale(Locale.of("fa", "IR"));
        result.setFallbackToSystemLocale(false);
        result.setUseCodeAsDefaultMessage(true);
        return result;
    }

    @Bean
    public PasswordEncoder applicationPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
