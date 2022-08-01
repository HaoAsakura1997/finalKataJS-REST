package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("userBoot");
        registry.addViewController("/admin").setViewName("manage");
        registry.addViewController("/login").setViewName("loginEmail");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
