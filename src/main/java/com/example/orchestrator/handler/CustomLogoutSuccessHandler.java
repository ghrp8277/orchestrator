package com.example.orchestrator.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        String redirectUrl = request.getHeader("Origin");
        if (redirectUrl == null || redirectUrl.isEmpty()) {
            redirectUrl = request.getHeader("Referer");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                redirectUrl = redirectUrl.substring(0, redirectUrl.indexOf("/", redirectUrl.indexOf("://") + 3));
            } else {
                redirectUrl = "http://localhost:3001";
            }
        }

        response.sendRedirect(redirectUrl + "/login");
    }
}

