package com.works.configs;

import com.works.services.RateLimitService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        String rateLimitKey = resolveRateLimitKey(request, response);

        Bucket bucket = rateLimitService.resolveBucket(rateLimitKey);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                  "error": "Too Many Requests",
                  "message": "1 request per second allowed"
                }
            """);
        }
    }

    private String resolveRateLimitKey(HttpServletRequest request,
                                       HttpServletResponse response) {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        // 🔐 Login olmuş
        if (auth != null && auth.isAuthenticated()) {
            return "AUTH_" + auth.getName();
        }

        // 👤 Anonymous
        String anonId = getOrCreateAnonId(request, response);
        return "ANON_" + anonId;
    }

    private String getOrCreateAnonId(HttpServletRequest request,
                                     HttpServletResponse response) {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("anon_id".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        // cookie yok → oluştur
        String anonId = UUID.randomUUID().toString();

        Cookie cookie = new Cookie("anon_id", anonId);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 1 gün
        cookie.setHttpOnly(false);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);
        return anonId;
    }
}
