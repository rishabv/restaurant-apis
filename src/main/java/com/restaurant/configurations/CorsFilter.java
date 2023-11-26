package com.restaurant.configurations;

import jakarta.servlet.Filter;
import org.junit.jupiter.api.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements WebMvcConfigurer {
    private final String url = "http://localhost:3000/*";

}
