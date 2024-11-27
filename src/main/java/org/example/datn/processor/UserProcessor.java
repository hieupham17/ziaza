package org.example.datn.processor;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.GioHang;
import org.example.datn.entity.Nhom;
import org.example.datn.entity.Profile;
import org.example.datn.entity.User;
import org.example.datn.exception.*;
import org.example.datn.jwt.JwtGenerator;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.enums.*;
import org.example.datn.model.request.AuthModel;
import org.example.datn.model.request.ChangePasswordModel;
import org.example.datn.model.request.RegisterModel;
import org.example.datn.model.request.UserQuery;
import org.example.datn.model.response.AuthInfoModel;
import org.example.datn.model.response.ProfileModel;
import org.example.datn.model.response.SessionModel;
import org.example.datn.model.response.UserModel;
import org.example.datn.processor.auth.AuthenticationChannelProvider;
import org.example.datn.processor.auth.AuthoritiesValidator;
import org.example.datn.repository.UserRepository;
import org.example.datn.service.*;
import org.example.datn.transformer.ProfileTransformer;
import org.example.datn.transformer.UserTransformer;
import org.example.datn.utils.CalendarUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.example.datn.utils.CalendarUtil.*;
import static org.example.datn.utils.CalendarUtil.DateTimeUtils.now;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author hoangKhong
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProcessor {

    final UserTransformer userTransformer;
    UserService userService;
    ProfileTransformer profileTransformer;
    ProfileService profileService;
    AuthenticationChannelProvider provider;
    JwtGenerator jwtGenerator;
    AuthoritiesValidator authoritiesValidator;
    NhomService nhomService;
    ChucNangService chucNangService;
    GioHangService gioHangService;


    public UserProcessor(UserTransformer userTransformer, UserService userService, ProfileTransformer profileTransformer, ProfileService profileService, AuthenticationChannelProvider provider, JwtGenerator jwtGenerator, AuthoritiesValidator authoritiesValidator, NhomService nhomService, ChucNangService chucNangService, GioHangService gioHangService) {
        this.userTransformer = userTransformer;
        this.userService = userService;
        this.profileTransformer = profileTransformer;
        this.profileService = profileService;
        this.provider = provider;
        this.jwtGenerator = jwtGenerator;
        this.authoritiesValidator = authoritiesValidator;
        this.nhomService = nhomService;
        this.chucNangService = chucNangService;
        this.gioHangService = gioHangService;
    }

    public List<UserModel> findByIdIn(List<Long> ids) {
        var userModel = userService.findByIdIn(ids).stream().map(mapper()).collect(Collectors.toList());
        return userModel;
    }

    public ServiceResult get(UserAuthentication ua) {
        var model = userService.findById(ua.getPrincipal())
                .map(mapper())
                .orElseThrow(() -> new EntityNotFoundException("not.fond"));
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getById(Long id) {
        var model = userService.findById(id)
                .map(mapper())
                .orElseThrow(() -> new EntityNotFoundException("not.fond"));
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public List<UserModel> findAll(String username) {
        var userModel = userService.findAll().stream().map(mapper()).collect(Collectors.toList());
        return userModel;
    }

    public ServiceResult getAll() {
        var models = userService.findAll().stream().map(mapper()).collect(Collectors.toList());
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public List<UserModel> getAcctive() {
        var userModel = userService.findAllByStatus(UserStatus.ACTIVE).stream().map(mapper()).collect(Collectors.toList());
        return userModel;
    }

    public UserModel findByUsername(String username) {
        return userService.findByUserName(username)
                .map(mapper())
                .orElseThrow(() -> new EntityNotFoundException("not.fond"));
    }

    private Function<User, UserModel> mapper() {
        return sub -> {
            var model = userTransformer.toModel(sub);

            var profile = profileTransformer.toModel(profileService.findByUserId(model.getId()).orElse(null));
            model.setProfile(profile);

            return model;
        };
    }

    public UserModel findById(Long id) {
        return userService.findById(id)
                .map(userEntity -> {
                    UserModel userModel = new UserModel();

                    BeanUtils.copyProperties(userEntity, userModel);

                    Profile profile = profileService.findByUserId(userEntity.getId()).orElse(null);
                    ProfileModel profileModel = new ProfileModel();
                    BeanUtils.copyProperties(profile, profileModel);
                    userModel.setProfile(profileModel);

                    return userModel;
                })
                .orElseThrow(() -> new EntityNotFoundException("not found"));
    }

    public AuthInfoModel authByFacebook(String token) throws Exception {
        return authNonRemember(() -> token, UserType.FB);
    }

    private AuthInfoModel authNonRemember(Supplier<?> input, UserType type) throws Exception {
        return auth(input, type, false);
    }

    private AuthInfoModel auth(Supplier<?> input, UserType type, boolean rememberMe) throws Exception {
        var processor = provider.getProcessor(type);

        var userModel = processor.auth(input);

        var token = type == UserType.NORMAL ?
                makeJwt(userModel, rememberMe) :
                makeJwt(userModel.getRole().name() + type, userModel, rememberMe);//Ensure facebookId, googleId or another openId is secure

        return AuthInfoModel.builder()
                .email(userModel.getProfile().getEmail())
                .username(userModel.getUserName())
                .token(token)
                .name(userModel.getProfile().getHoVaTen())
                .avatar(userModel.getProfile().getAvatar())
                .build();
    }

    private String makeJwt(UserModel userModel, boolean rememberMe) {
        return makeJwt(null, userModel, rememberMe);
    }

    private String makeJwt(String sub, UserModel userModel, boolean rememberMe) {
        if (Objects.isNull(userModel.getProfile())) {
            var profile = profileService.findByUserId(userModel.getId()).orElse(new Profile());
            var profileModel = profileTransformer.toModel(profile);
            userModel.setProfile(profileModel);
        }
        var profile = userModel.getProfile();
        return jwtGenerator.gen(userModel.getId(), Objects.nonNull(sub) ? sub : userModel.getUserName(),
                profile.getHoVaTen(), Collections.emptyList(), profile.getEmail(), profile.getPhone(), "", rememberMe);
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult register(RegisterModel model) throws DuplicatedException {
        validatePassword(model);
        validateDuplicated(model);

        var hashedPass = userService.encodePassword(model.getPassword());
        var user = new User();
        user.setUserName(model.getEmail());
        user.setPassword(hashedPass);
        user.setRole(model.getRole() != null ? model.getRole() : UserRoles.CLIENT);
        user.setStatus(UserStatus.ACTIVE);
        user.setType(UserType.NORMAL);
        user.setXacThuc(false);
        user.setNgayTao(LocalDateTime.now());
        user.setNgayCapNhat(LocalDateTime.now());
        userService.save(user);

        var profile = new Profile();
        profile.setUserId(user.getId());
        profile.setEmail(model.getEmail());
        profile.setPhone(model.getPhone());
        profile.setNgaySinh(model.getNgaySinh());
        profile.setHoVaTen(model.getName());
        profile.setNgayTao(LocalDateTime.now());
        profile.setNgayCapNhat(LocalDateTime.now());
        profileService.save(profile);

        var gioGang = new GioHang();
        gioGang.setIdNguoiDung(user.getId());
        gioHangService.save(gioGang);
        return new ServiceResult(SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public AuthInfoModel auth(AuthModel authModel) throws Exception {
        synchronized (this) {
            var processor = provider.getProcessor(UserType.NORMAL);
            var userModel = processor.auth(() -> authModel);
            var profile = profileService.findByUserId(userModel.getId()).orElseThrow(() -> new EntityNotFoundException("not.found"));
            ProfileModel profileModel = new ProfileModel();
            BeanUtils.copyProperties(profile, profileModel);
            userModel.setProfile(profileModel);

            var exp = now().plusDays(1L).with(LocalTime.MAX);
            List<String> type = new ArrayList<>();
            type.add(userModel.getRole().toString());
            var token = makeJwt(userModel, type, exp);
            var mapToken = makeMapJwt(userModel, now().plusMinutes(10L));

            var groupIds = nhomService.findNhomByUserId(userModel.getId()).stream().map(Nhom::getId).collect(Collectors.toList());
            var authorities = chucNangService.findChucNangByNhomIds(groupIds);
            authorities.add(userModel.getRole().name());
            setSession(userModel, exp, authorities);
            setActivity(userModel);
            return AuthInfoModel.builder()
                    .username(authModel.getUsername())
                    .email(profile.getEmail())
                    .username(userModel.getUserName())
                    .token(token)
                    .name(profile.getHoVaTen())
                    .role(userModel.getRole())
                    .avatar(profile.getAvatar())
                    .mapToken(mapToken)
                    .auths(authorities)
                    .build();
        }
    }

    private void setActivity(UserModel userModel) {
        try {
            CompletableFuture.runAsync(() -> {
                authoritiesValidator.saveActivity(userModel.getId(), userModel.getUserName(),
                        ActivityType.LOGIN.name(), ActivityType.LOGIN.name()
                        , HttpMethod.POST.name(), SystemConstant.PATH_LOGIN_ADMIN);
            });
        } catch (Exception ignored) {
        }
    }

    private void setSession(UserModel userModel, LocalDateTime exp, List<String> authorities) {


        var duration = Duration.between(LocalDateTime.now(), exp);
        var session = SessionModel.builder()
                .duration(duration.getSeconds())
                .auths(authorities)
                .userId(userModel.getId())
                .build();

        authoritiesValidator.saveSession(userModel.getId(), session);
    }

    private String makeJwt(UserModel userModel, List<String> authorities, LocalDateTime exp) {
        var profile = profileService.findByUserId(userModel.getId()).orElse(new Profile());
        return jwtGenerator.gen(userModel.getId(), userModel.getUserName(),
                profile.getHoVaTen(), authorities, profile.getEmail(), profile.getPhone(), userModel.getRole().name(), exp);
    }

    private String makeMapJwt(UserModel userModel, LocalDateTime exp) {
        if (Objects.isNull(userModel.getProfile())) {
            var profile = profileService.findByUserId(userModel.getId()).orElse(new Profile());
            var profileModel = profileTransformer.toModel(profile);
            userModel.setProfile(profileModel);
        }
        var profile = userModel.getProfile();
        return jwtGenerator.gen(userModel.getId(), userModel.getUserName(),
                profile.getHoVaTen(), Collections.emptyList(), profile.getEmail(), profile.getPhone(), SystemConstant.ROLE_LAYER_MAP_VIEW, exp);
    }

    private void validatePassword(RegisterModel model) {
        if (!model.getPassword().equals(model.getRetypePassword())) {
            throw new IllegalArgumentException("password.not-matched");
        }
    }

    private void validateDuplicated(RegisterModel model) throws DuplicatedException {
        if (userService.exitsUserName(model.getEmail())) {
            throw DuplicatedException.of("user.duplicated");
        }
        if (StringUtils.isNotBlank(model.getPhone())) {
            if (profileService.phoneExists(model.getPhone())) {
                throw DuplicatedException.of("phone.used");
            }
        }
    }

    public ServiceResult changePassword(ChangePasswordModel model, UserAuthentication ua)
            throws NotFoundEntityException, AccessDeniedException, InputInvalidException, ActionNotAllowAttemptException {
        if (!model.getPassword().equals(model.getRetypePassword())) {
            throw InputInvalidException.of("password.not-matched");
        }

        var user = userService.getActiveOrElseThrow(ua.getPrincipal());
        if (!user.isNormalType()) {
            throw ActionNotAllowAttemptException.of("user-type.does-not-support.action");
        }

        if (!userService.passwordMatched(model.getOldPassword(), user)) {
            throw AccessDeniedException.of("old-password.not-matched");
        }

        var hashedPass = userService.encodePassword(model.getPassword());
        user.setPassword(hashedPass);
        userService.save(user);
        return new ServiceResult();
    }

    public ServiceResult getListByRole(UserRoles role) {
        var users = userService.findByRole(role);
        var models = users.stream().map(user -> {
            var model = userTransformer.toModel(user);
            var profile = profileService.findByUserId(user.getId()).orElse(null);
            var profileModel = profileTransformer.toModel(profile);
            model.setProfile(profileModel);
            return model;
        }).collect(Collectors.toList());
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult deleteById(Long id) {
        var user = userService.findById(id).orElseThrow(() -> new EntityNotFoundException("not.fond"));
        userService.delete(user);
        return new ServiceResult();
    }

    public ServiceResult getList(UserQuery request) {
        var users = userService.getList(request.getKeyword(), request.getRole(), request.getPhone(), request.getPage(), request.getSize());
        var models = users.stream().map(mapper()).collect(Collectors.toList());
        var totalPages = (int) Math.ceil((double) users.getTotalElements() / request.getSize());
        return new ServiceResult(models, totalPages, request.getPage(), users.getTotalElements(), SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult changeStatus(Long id, UserStatus status) {
        var user = userService.findById(id).orElseThrow(() -> new EntityNotFoundException("not.fond"));
        user.setStatus(status);
        userService.save(user);
        return new ServiceResult();
    }


}
