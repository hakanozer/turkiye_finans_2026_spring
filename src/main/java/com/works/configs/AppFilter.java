package com.works.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Writer;

@Configuration
public class AppFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // path
        String path = req.getServletPath();
        boolean loginStatus = true;
        if (path.startsWith("/customer")) {
            loginStatus = false;
        }

        if (loginStatus) {
            // oturum denetimi - session
            Object sessionStatus = req.getSession().getAttribute("customer");
            if (sessionStatus == null) {
                // return json content
                Writer writer = res.getWriter();
                res.setContentType("application/json");
                writer.write("{ \"error\": \"Login Error\" }"); // hata mesajı
                res.setStatus(401);
            }else {
                filterChain.doFilter(req, res);
            }
        }else {
            filterChain.doFilter(req, res);
        }

    }

}
