package org.example.datn.processor.auth;

import org.example.datn.model.response.SessionModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;

import java.util.List;

public interface AuthoritiesValidator {
    List<String> getAuthorities(Long userId);
    Boolean verifySession(Long userId);
    Boolean verifyAdminRoles(String role);
    void saveSession(Long userId, SessionModel sessionModel);
    void deleteSession(Long userId);
    void saveActivity(Long userId, String username, HandlerExecutionChain executionChain, String path);
    void saveActivity(Long userId, String username, String activityType, String action, String method, String path);
}
