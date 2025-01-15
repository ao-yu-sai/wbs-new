package com.ai_offshore.tools.wbs.web.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class SpringRequestContext {
    private final String requestUri;
    
    public SpringRequestContext(HttpServletRequest request) {
        this.requestUri = request.getRequestURI();
    }
} 