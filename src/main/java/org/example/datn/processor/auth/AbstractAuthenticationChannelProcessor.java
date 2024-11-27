package org.example.datn.processor.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.Profile;
import org.example.datn.entity.User;
import org.example.datn.exception.CommonException;
import org.example.datn.exception.NotSupportedException;
import org.example.datn.model.enums.UserRoles;
import org.example.datn.model.enums.UserType;
import org.example.datn.model.response.UserModel;
import org.example.datn.service.ProfileService;
import org.example.datn.service.UserService;
import org.example.datn.transformer.ProfileTransformer;
import org.example.datn.transformer.UserTransformer;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.springframework.util.StringUtils.isEmpty;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class AbstractAuthenticationChannelProcessor implements AuthenticationChannelProcessor {
    UserType type;
    UserService userService;
    UserTransformer userTransformer;
    ProfileService profileService;
    ProfileTransformer profileTransformer;

    protected AbstractAuthenticationChannelProcessor(UserType type, AuthenticationChannelProvider provider,
                                                     UserService userService,
                                                     UserTransformer userTransformer,
                                                     ProfileService profileService,
                                                     ProfileTransformer profileTransformer

    ) {
        this.userService = userService;
        this.userTransformer = userTransformer;
        this.profileService = profileService;
        this.profileTransformer = profileTransformer;

        this.type = type;

        provider.register(type, this);
    }

    @Override
    public UserModel auth(Supplier<?> input) throws CommonException {
        throw NotSupportedException.of("auth.method.not-supported");
    }

    protected User createUserAndProfileIfNotExists(String username, Function<User, Profile> transformer) {
        var user = userService.findByUserName(username)
                .orElseGet(() -> userTransformer.toActiveEntity(username, type, UserRoles.USER));

        if (isNotExistsInDb(user)) {
            var profile = transformer.apply(user);
            if (!isEmpty(profile.getEmail())) {
                user.setXacThuc(true);
            }

            var userSaved = userService.saveEntity(user);

            profile.setId(userSaved.getId());
            profileService.save(profile);
        }
        return user;
    }

    private boolean isNotExistsInDb(User user) {
        return Objects.isNull(user.getId());
    }
}
