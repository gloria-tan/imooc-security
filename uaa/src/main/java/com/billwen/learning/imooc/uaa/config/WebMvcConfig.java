package com.billwen.learning.imooc.uaa.config;

import lombok.RequiredArgsConstructor;
import org.passay.MessageResolver;
import org.passay.spring.SpringMessageResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MessageSource messageSource;

    private final Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/")
                .resourceChain(false);

        registry.setOrder(1);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login")
                .setViewName("loginPage");

        registry.addViewController("/").setViewName("index");

        registry.setOrder(1);
    }

    @Bean
    public MessageResolver messageResolver() {
        return new SpringMessageResolver(this.messageSource);
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    /**
     * 使用 Spring Mvc 配置 CORS
     * 也可以在 Security Config 中进行配置
     *
     * @param registry Cors注册表
     */
    /*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (environment.acceptsProfiles(Profiles.of("dev", "test"))) {
            registry.addMapping("/**")
                    .allowedHeaders("*")
                    .exposedHeaders("X-Authenticate")
                    .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                    .allowedOrigins("http://localhost:4001");
        } else {
            registry.addMapping("/**")
                    .allowedHeaders("*")
                    .exposedHeaders("X-Authenticate")
                    .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                    .allowedOrigins("https://uaa.imooc.com");
        }
    }

     */
}
