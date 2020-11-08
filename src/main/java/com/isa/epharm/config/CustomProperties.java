package com.isa.epharm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Configuration
@EnableConfigurationProperties({CustomProperties.class})
@ConfigurationProperties(prefix = "properties", ignoreUnknownFields = false)
public class CustomProperties {

    @NotBlank
    private String jwtSecret;

    @Positive
    private int jwtExpirationInMs;

    @Positive
    private Long maxAgeSecs;

    @NotBlank
    private String clientUrl;

    private final Amazon amazon = new Amazon();

    private final Async async = new Async();

    @Data
    public static class Async {

        @NotNull
        @PositiveOrZero
        private int maxPoolSize;
        @NotNull
        @PositiveOrZero
        private int corePoolSize;
    }

    @Data
    public static class Amazon {
        @NotBlank
        private String accessKey;
        @NotBlank
        private String secretKey;
        @NotBlank
        private String region;
        @NotBlank
        private String s3Endpoint;
        @NotBlank
        private String bucket;
    }
}
