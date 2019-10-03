package com.example.websocket.configuration;

import com.example.websocket.WebSocketApplication;
import com.google.common.base.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private final Log logger = LogFactory.getLog(getClass());

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .directModelSubstitute(Instant.class, long.class)
                .select()
                .apis(publicResources())
                .build();
    }

    private Predicate<RequestHandler> publicResources() {
        return RequestHandlerSelectors.basePackage("com.example.websocket");
    }

    private String getVersion() {
        String version = null;
        try {
            Manifest manif = new Manifest(WebSocketApplication.class.getResourceAsStream("/META-INF/MANIFEST.MF"));
            version = (String) manif.getMainAttributes().get(Attributes.Name.IMPLEMENTATION_VERSION);
        } catch (IOException e) {
            logger.warn("Error check version", e);
        }
        return version;
    }
}
