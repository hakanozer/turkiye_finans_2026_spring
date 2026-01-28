package com.works.configs;

import com.works.entities.Customer;
import com.works.entities.Role;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

//@Configuration
public class AppFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AppFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // 🔥 Request detaylarını logla
        logRequestAsJson(req);

        // path
        String path = req.getServletPath();
        boolean loginStatus = true;
        if (path.startsWith("/customer")) {
            loginStatus = false;
        }

        if (loginStatus) {
            // oturum denetimi - session
            Object customerObj = req.getSession().getAttribute("customerObj");
            if (customerObj == null) {
                // return json content
                Writer writer = res.getWriter();
                res.setContentType("application/json");
                writer.write("{ \"error\": \"Login Error\" }"); // hata mesajı
                res.setStatus(401);
            }else {
                // role denetimi yap
                Customer customer = (Customer) customerObj;
                List<Role> roles = customer.getRoles();
                boolean rolesStatus = false;
                for (Role roleObj : roles) {
                    String role = roleObj.getName().replace("ROLE_", "");
                    if(path.contains(role)) {
                        rolesStatus = true;
                        break;
                    }
                }
                if (rolesStatus) {
                    filterChain.doFilter(req, res);
                }else {
                    Writer writer = res.getWriter();
                    res.setContentType("application/json");
                    writer.write("{ \"error\": \"Role Permission Dined\" }"); // hata mesajı
                    res.setStatus(403);
                }

            }
        }else {
            filterChain.doFilter(req, res);
        }

    }


    private void logRequestAsJson(HttpServletRequest req) {

        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"event\":\"HTTP_REQUEST\",");
        json.append("\"timestamp\":").append(System.currentTimeMillis()).append(",");
        json.append("\"method\":\"").append(req.getMethod()).append("\",");
        json.append("\"path\":\"").append(req.getRequestURI()).append("\",");
        json.append("\"query\":\"").append(req.getQueryString()).append("\",");
        json.append("\"ip\":\"").append(req.getRemoteAddr()).append("\",");
        json.append("\"forwardedIp\":\"").append(req.getHeader("X-Forwarded-For")).append("\",");
        json.append("\"language\":\"").append(req.getHeader("Accept-Language")).append("\",");
        json.append("\"userAgent\":\"").append(req.getHeader("User-Agent")).append("\",");

        // Headers
        json.append("\"headers\":{");
        var headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            json.append("\"")
                    .append(header)
                    .append("\":\"")
                    .append(req.getHeader(header).replace("\"", "'"))
                    .append("\",");
        }
        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");

        json.append("}");

        logger.info(json.toString());
    }



}
