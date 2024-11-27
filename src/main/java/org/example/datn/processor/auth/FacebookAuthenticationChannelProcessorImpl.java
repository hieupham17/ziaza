package org.example.datn.processor.auth;

import org.example.datn.model.enums.UserType;
import org.example.datn.service.ProfileService;
import org.example.datn.service.UserService;
import org.example.datn.transformer.ProfileTransformer;
import org.example.datn.transformer.UserTransformer;

public class FacebookAuthenticationChannelProcessorImpl extends AbstractAuthenticationChannelProcessor{
    protected FacebookAuthenticationChannelProcessorImpl(AuthenticationChannelProvider provider, UserService userService, UserTransformer userTransformer, ProfileService profileService, ProfileTransformer profileTransformer) {
        super(UserType.FB, provider, userService, userTransformer, profileService, profileTransformer);
    }
}
