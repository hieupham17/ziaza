package org.example.datn.processor.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.datn.exception.AccessDeniedException;
import org.example.datn.model.enums.UserType;
import org.example.datn.model.request.AuthModel;
import org.example.datn.model.response.UserModel;
import org.example.datn.service.ProfileService;
import org.example.datn.service.UserService;
import org.example.datn.transformer.ProfileTransformer;
import org.example.datn.transformer.UserTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.function.Supplier;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NormalAuthenticationChannelProcessorImpl extends AbstractAuthenticationChannelProcessor {

    protected NormalAuthenticationChannelProcessorImpl(AuthenticationChannelProvider provider, UserService userService, UserTransformer userTransformer, ProfileService profileService, ProfileTransformer profileTransformer) {
        super(UserType.NORMAL, provider, userService, userTransformer, profileService, profileTransformer);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserModel auth(Supplier<?> input) throws AccessDeniedException {
        var auth = (AuthModel) input.get();

        var user = userService.getActive(auth.getUsername())
                .orElseThrow(AccessDeniedException.ofSupplier("user-lock-account"));

        if (!userService.passwordMatched(auth.getPassword(), user)) {
            throw AccessDeniedException.of("auth.fail");
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        return userModel;
    }
}
