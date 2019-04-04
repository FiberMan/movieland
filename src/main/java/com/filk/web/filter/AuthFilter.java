package com.filk.web.filter;

import com.filk.entity.Session;
import com.filk.service.SecurityService;
import com.filk.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

public class AuthFilter implements HandlerInterceptor {
    private SecurityService securityService;

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("uuid");
        String method = request.getMethod();
        String pathInfo = request.getPathInfo();

        if ("/review".equals(pathInfo) && "POST".equals(method)) {
            final List<UserRole> acceptedRoles = Collections.singletonList(UserRole.USER);

            Session session = securityService.checkPermission(token, acceptedRoles);
            request.setAttribute("session", session);
        }

        return true;
    }
}