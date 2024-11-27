package org.example.datn.processor.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.UserType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationChannelProvider {
    Map<UserType, AuthenticationChannelProcessor> MAP = new HashMap<>();

    public void register(UserType type, AuthenticationChannelProcessor processor) {
        MAP.put(type, processor);
    }

    public AuthenticationChannelProcessor getProcessor(UserType type) {
        return MAP.get(type);
    }
}
