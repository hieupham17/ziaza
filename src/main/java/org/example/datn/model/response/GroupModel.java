package org.example.datn.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupModel implements Serializable {

    Long id;
    @JsonProperty("ten")
    String ten;
    @JsonProperty("moTa")
    String moTa;
    @JsonProperty("trangThai")
    Integer trangThai;
    @JsonProperty("count")
    Integer count;
    @JsonProperty("idChucNang")
    List<Long> idChucNang;
    @JsonProperty("idNguoiDung")
    List<Long> userId;
    @JsonProperty("listChucNangModel")
    List<FunctionModel> functionModelList;
    @JsonProperty("listNguoiDungModel")
    List<UserModel> userModelList;
}
