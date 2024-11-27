package org.example.datn.transformer;

import org.example.datn.entity.Profile;
import org.example.datn.model.enums.UserStatus;
import org.example.datn.model.request.ProfileRequest;
import org.example.datn.model.response.ProfileModel;
import org.example.datn.utils.CalendarUtil;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author hoangKhong
 */
@Mapper(componentModel = "spring")
@Component
public interface ProfileTransformer {

    Profile toEntity(Long userId, String phone, String email, LocalDate ngaySinh);

    Profile toEntity(Long userId, String name);

    ProfileModel toModel(Profile profile);

    Profile toEntity(ProfileRequest request, Long id);
}
