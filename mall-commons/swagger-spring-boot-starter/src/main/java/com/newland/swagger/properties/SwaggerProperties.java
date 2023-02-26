package com.newland.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerProperties {
    private Boolean enable;
    private String version;
    private String title;
    private String basePackage;
    private String description;
    private Concat concat;

    @Data
    public static class Concat {
        private String name;
        private String url;
        private String email;
    }
}
