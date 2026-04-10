package com.example.showcase.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Component
public class JwtPublicPathsConfig {

    @Getter
    @Value("${jwt.public-paths}")
    private List<String> paths;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public boolean isPublic(String path) {
        return paths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
