package kr.ac.kopo.wodyd.bookmarket.config;


import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Value("${file.uploadDir}")
    String fileDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/BookMarket/imgs/**")
                .addResourceLocations("file:///" + fileDir)
                .setCachePeriod(60 * 60 * 24 *365);
    }
}
