package com.hm.packer.application.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class FilterProcessingMatcher implements RequestMatcher {
    private OrRequestMatcher processingMatcher;

    public FilterProcessingMatcher(List<String> processingPath){
        this.processingMatcher = new OrRequestMatcher(processingPath.stream().map(p -> new AntPathRequestMatcher(p)).collect(Collectors.toList()));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return processingMatcher.matches(request);
    }
}
