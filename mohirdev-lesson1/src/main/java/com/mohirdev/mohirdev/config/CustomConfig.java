package com.mohirdev.mohirdev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configuration
public class CustomConfig {

    @Bean(name = "customConversionService")
    public FormattingConversionServiceFactoryBean customConversionService() {
        FormattingConversionServiceFactoryBean conversionServiceFactoryBean = new FormattingConversionServiceFactoryBean();
        // Add any custom converters or configuration to the FormatterRegistry
        conversionServiceFactoryBean.setRegisterDefaultFormatters(true); // Example: Register default formatters
        return conversionServiceFactoryBean;
    }
}
