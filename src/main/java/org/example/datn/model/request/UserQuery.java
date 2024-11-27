package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.UserRoles;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserQuery extends CommonQuery {
    UserRoles role;
    String phone;
}
