package com.app.mediator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/expense-tracter/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET","POST")
                .allowedHeaders("Origin","Content-Type","Authorization")
                .allowCredentials(true);
    }
}
