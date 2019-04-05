package com.filk.web.filter;

import com.filk.entity.Session;
import com.filk.service.SecurityService;
import com.filk.util.UserHolder;
import com.filk.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

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

        List<UserRole> acceptedRoles = getAcceptedRoles(handler);
        if (acceptedRoles.isEmpty()) {
            return true;
        }

        Optional<Session> session = securityService.getSession(token, acceptedRoles);
        if (!session.isPresent()) {
            response.sendRedirect("/login");
            return false;
        }

        UserHolder.set(session.get().getUser());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserHolder.remove();
    }

    private List<UserRole> getAcceptedRoles(Object handler) {
        List<UserRole> acceptedRoles = new ArrayList<>();

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(ProtectedBy.class)) {
                ProtectedBy annotation = method.getAnnotation(ProtectedBy.class);
                acceptedRoles = Arrays.asList(annotation.userRole());
            }
        }

        return acceptedRoles;
    }
}