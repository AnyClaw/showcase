package com.example.showcase.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtPublicPathsConfig {

    @Getter @Setter
    private List<String> publicPaths;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public boolean isPublic(String path) {
        return publicPaths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
