package org.example.datn.processor;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.ChucNang;
import org.example.datn.entity.Nhom;
import org.example.datn.exception.DuplicatedException;
import org.example.datn.exception.NotFoundEntityException;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.GroupQueryModel;
import org.example.datn.model.response.FunctionModel;
import org.example.datn.model.response.GroupModel;
import org.example.datn.model.response.RegisterUpdateGroupModel;
import org.example.datn.model.response.UserModel;
import org.example.datn.service.*;
import org.example.datn.transformer.ChucNangTransformer;
import org.example.datn.transformer.NhomTransformer;
import org.example.datn.transformer.ProfileTransformer;
import org.example.datn.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NhomProcessor {

    NhomService service;
    NhomTransformer nhomTransformer;
    NhomChucNangService nhomChucNangService;
    NhomNguoiDungService nhomNguoiDungService;
    UserTransformer userTransformer;
    UserService userService;
    ProfileService profileService;
    ProfileTransformer profileTransformer;
    ChucNangService chucNangService;
    ChucNangTransformer chucNangTransformer;

    public NhomProcessor(NhomService service, NhomTransformer nhomTransformer, NhomChucNangService nhomChucNangService, NhomNguoiDungService nhomNguoiDungService, UserTransformer userTransformer, UserService userService, ProfileService profileService, ProfileTransformer profileTransformer, ChucNangService chucNangService, ChucNangTransformer chucNangTransformer) {
        this.service = service;
        this.nhomTransformer = nhomTransformer;
        this.nhomChucNangService = nhomChucNangService;
        this.nhomNguoiDungService = nhomNguoiDungService;
        this.userTransformer = userTransformer;
        this.userService = userService;
        this.profileService = profileService;
        this.profileTransformer = profileTransformer;
        this.chucNangService = chucNangService;
        this.chucNangTransformer = chucNangTransformer;
    }

    public ServiceResult getList(GroupQueryModel model) {
        var dataReturn = service.getList(model.getKeyword(), model.getPage(), model.getSize());
        var groups = dataReturn.getContent();
        List<GroupModel> groupModels = groups.stream()
                .map(group -> {
                    GroupModel model2 = nhomTransformer.toModel(group);

                    //lấy danh sách thành viên cho nhóm
                    List<UserModel> userModelList = getUserModelsForGroup(group);
                    // đếm số thành viên trong nhóm
                    int memberCount = userModelList.size();
                    model2.setCount(memberCount);
                    // Gán giá trị cho functionModelList
                    List<FunctionModel> functionModelList = null;
                    try {
                        functionModelList = getFunctionModelsForGroup(group);
                    } catch (NotFoundEntityException e) {
                        e.printStackTrace();
                    }
                    model2.setFunctionModelList(functionModelList);

                    // Gán giá trị cho userModelList
                    model2.setUserModelList(userModelList);

                    //Gán giá trị cho idChucNang và userId
                    List<Long> functionIds = functionModelList.stream().map(FunctionModel::getId).collect(Collectors.toList());
                    List<Long> userIds = userModelList.stream().map(UserModel::getId).collect(Collectors.toList());
                    model2.setIdChucNang(functionIds);
                    model2.setUserId(userIds);
                    return model2;
                })
                .collect(Collectors.toList());
        var totalPages = (int) Math.ceil((double) dataReturn.getTotalElements() / model.getSize());
        return new ServiceResult(groupModels, totalPages, model.getPage(), dataReturn.getTotalElements(), SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    private List<FunctionModel> getFunctionModelsForGroup(Nhom nhom) throws NotFoundEntityException {
        List<ChucNang> functions = chucNangService.findAllByIdNhom(nhom.getId());
        List<FunctionModel> functionModelList = functions.stream()
                .map(function -> chucNangTransformer.toModel(function))
                .collect(Collectors.toList());

        return functionModelList;
    }

    // Phương thức để lấy userModelList cho một nhóm cụ thể
    private List<UserModel> getUserModelsForGroup(Nhom nhom) {
        List<Long> userIds = nhomNguoiDungService.findByGroupId(nhom.getId());
        List<UserModel> userModelList = userIds.stream()
                .map(userId -> {
                    var user = userService.findById(userId).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng"));
                    var userModel = userTransformer.toModel(user);
                    var profile = profileTransformer.toModel(profileService.findById(userModel.getId()).orElse(null));
                    userModel.setProfile(profile);
                    return userModel;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return userModelList;
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult add(RegisterUpdateGroupModel model) throws DuplicatedException {
        validateDuplicate(model, null);
        var nhom = service.save(nhomTransformer.toEntity(model));
        nhom.setTrangThai(SystemConstant.ACTIVE);
        nhom.setNgayTao(LocalDateTime.now());
        nhom.setNgayCapNhat(LocalDateTime.now());
        addGroupFunction(model.getChucNangId(), nhom.getId(), model.getUserId());
        return new ServiceResult(SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public void addGroupFunction(List<Long> functionId, Long groupId, List<Long> userId) {
        nhomChucNangService.deleteAllByNhomId(groupId);
        if (!functionId.isEmpty()) {
            functionId.forEach(item -> nhomChucNangService.save(groupId, item));
        }
        if (!userId.isEmpty()) {
            userId.forEach(item -> nhomNguoiDungService.save(groupId, item));
        }

    }

    private void validateDuplicate(RegisterUpdateGroupModel model, Long id) throws DuplicatedException {
        if (Objects.isNull(id)) {
            if (service.existsAllByTen(model.getTen())) {
                throw DuplicatedException.of("name.group.duplicated");
            }
        } else {
            if (service.existsAllByTenAndIdNot(model.getTen(), id)) {
                throw DuplicatedException.of("name.group.duplicated");
            }
        }
    }


    public ServiceResult changeStatus(Long id, Integer trangThai) {
        var group = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhóm"));
        group.setTrangThai(trangThai);
        service.save(group);
        return new ServiceResult();
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult edit(RegisterUpdateGroupModel model, Long id) throws NotFoundEntityException, DuplicatedException {
        var group = service.findById(id)
                .orElseThrow(NotFoundEntityException.ofSupplier("not.found"));
        group.setTen(model.getTen());
        group.setMoTa(model.getMoTa());
        group.setNgayCapNhat(LocalDateTime.now());
        validateDuplicate(model, id);
        service.save(group);
        editGroupFunction(model.getChucNangId(), id, model.getUserId());
        return new ServiceResult(SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public void editGroupFunction(List<Long> functionId, Long groupId, List<Long> userId) {
        nhomChucNangService.deleteAllByNhomId(groupId);
        if (!functionId.isEmpty()) {
            functionId.forEach(item -> nhomChucNangService.save(groupId, item));
        }
        nhomNguoiDungService.deleteAllByIdNhom(groupId);
        if (!userId.isEmpty()) {
            userId.forEach(item -> nhomNguoiDungService.save(groupId, item));
        }
    }

}
