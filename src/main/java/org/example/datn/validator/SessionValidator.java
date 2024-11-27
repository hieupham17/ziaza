package org.example.datn.validator;

import lombok.AccessLevel;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.FieldDefaults;
import org.example.datn.constants.SystemConstant;
import org.example.datn.model.enums.ActivityType;
import org.example.datn.model.enums.UserRoles;
import org.example.datn.model.response.ActivityModel;
import org.example.datn.model.response.SessionModel;
import org.example.datn.processor.auth.AuthoritiesValidator;
import org.example.datn.utils.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionValidator implements AuthoritiesValidator {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<String> getAuthorities(Long userId) {
        var session = (SessionModel) redisTemplate.opsForValue().get(userId);
        if (Objects.isNull(session)) return null;
        return session.getAuths();
    }

    @Override
    public Boolean verifySession(Long userId) {
        var expire = redisTemplate.getExpire(userId);
        if (Objects.isNull(expire)) return false;
        return expire > 0;
    }

    @Override
    public Boolean verifyAdminRoles(String role) {
        return UserRoles.ADMIN.name().equals(role);
    }

    @Override
    public void saveSession(Long userId, SessionModel session) {
        redisTemplate.delete(userId);
        redisTemplate.opsForValue().set(userId, session);
        redisTemplate.expire(userId, session.getDuration(), TimeUnit.SECONDS);
    }

    @Override
    public void deleteSession(Long userId) {
        redisTemplate.delete(userId);
    }

    @Override
    public void saveActivity(Long userId, String username, HandlerExecutionChain executionChain, String path) {
        var activityModel = getActivityModel(userId, username, executionChain, path);
        redisTemplate.opsForList().rightPush(SystemConstant.KEY_CACHE_ACTIVITY, activityModel);
    }

    @Override
    public void saveActivity(Long userId, String username, String activityType, String action, String method, String path) {
        var activityModel = getActivityModel(userId, username, activityType, action, method, path);
        redisTemplate.opsForList().rightPush(SystemConstant.KEY_CACHE_ACTIVITY, activityModel);
    }

    private ActivityModel getActivityModel(Long userId, String username, String activityType, String action, String method, String path) {
        return ActivityModel.builder()
                .userId(userId)
                .username(username)
                .action(action)
                .method(method)
                .path(path)
                .ngayTao(CalendarUtil.DateTimeUtils.now())
                .activityType(ActivityType.valueOf(activityType))
                .build();
    }

    private ActivityModel getActivityModel(Long userId, String username, HandlerExecutionChain executionChain, String path) {
        var execution = (HandlerMethod) executionChain.getHandler();
        var apiAnnotation = execution.getMethod().getDeclaredAnnotation(ApiOperation.class);
        return ActivityModel.builder()
                .userId(userId)
                .username(username)
                .action(apiAnnotation != null ? apiAnnotation.value() : "")
                .method(execution.getMethod().getName().toUpperCase(Locale.ROOT))
                .path(path)
                .ngayTao(CalendarUtil.DateTimeUtils.now())
                .activityType(ActivityType.NORMAL)
                .build();
    }
}
