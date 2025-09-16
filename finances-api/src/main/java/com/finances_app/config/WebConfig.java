package com.finances_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration indicates that this class contains Spring Framework configuration.
@Configuration
// @EnableWebMvc enables Spring's web MVC framework, allowing us to customize it.
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // Overrides the default method to provide custom CORS configuration.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // This configures Cross-Origin Resource Sharing (CORS) for the application.
        registry.addMapping("/api/**") // Applies the CORS rules to all endpoints under "/api/".
                .allowedOrigins("*") // Allows requests from any origin (e.g., any website).
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specifies which HTTP methods are permitted.
                .allowedHeaders("*"); // Allows all headers in the request.
    }
}