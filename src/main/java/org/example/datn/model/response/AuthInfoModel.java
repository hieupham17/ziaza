package org.example.datn.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.UserRoles;

import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthInfoModel {
    @JsonProperty("tenDangNhap")
    String username;
    @JsonProperty("ten")
    String name;
    @JsonProperty("email")
    String email;
    @JsonProperty("token")
    String token;
    @JsonProperty("mapToken")
    String mapToken;
    @JsonProperty("avatar")
    String avatar;
    @JsonProperty("vaiTro")
    UserRoles role;
    @JsonProperty("quyen")
    List<String> auths;
}
