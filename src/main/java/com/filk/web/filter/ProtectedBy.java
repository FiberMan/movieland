package com.filk.web.filter;

import com.filk.util.UserRole;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ProtectedBy {
    UserRole[] userRole();
}
