package org.example.datn.model.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ActivityType {
    NORMAL, LOGIN, LOGOUT, REGISTER
}
