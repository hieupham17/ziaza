package org.example.datn.transformer;

import org.example.datn.entity.User;
import org.example.datn.model.enums.UserRoles;
import org.example.datn.model.enums.UserStatus;
import org.example.datn.model.enums.UserType;
import org.example.datn.model.request.RegisterModel;
import org.example.datn.model.response.UserModel;
import org.example.datn.utils.CalendarUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author hoangKhong
 */
@Mapper(componentModel = "spring")
@Component
public interface UserTransformer {

//    @Mapping(target = "status", expression = "java(UserStatus.ACTIVE)")
//    @Mapping(target = "ngayTao", expression = "java(DateTimeUtils.now())")
    User toActiveEntity(String userName, UserType type, UserRoles role);

    UserModel toModel(User user);

    User toUser(RegisterModel model, String hashedPassword);
}
