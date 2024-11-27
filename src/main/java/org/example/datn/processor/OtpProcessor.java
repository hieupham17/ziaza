package org.example.datn.processor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.datn.entity.Otp;
import org.example.datn.entity.OtpRetry;
import org.example.datn.exception.ActionNotAllowAttemptException;
import org.example.datn.exception.InputInvalidException;
import org.example.datn.exception.NotFoundEntityException;
import org.example.datn.model.enums.OtpType;
import org.example.datn.model.request.ActiveByEmailModel;
import org.example.datn.model.request.ResetPasswordModel;
import org.example.datn.service.*;
import org.springframework.stereotype.Component;
import org.example.datn.utils.CalendarUtil.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.isEmpty;
import static org.example.datn.utils.CalendarUtil.DateTimeUtils.now;
import static org.example.datn.utils.CalendarUtil.DateTimeUtils.toJavaUtilDate;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtpProcessor {

    OtpService service;
    EmailService emailService;
    ProfileService profileService;
    UserService userService;
    OtpRetryService otpRetryService;

    static Map<String, Object> userLock = new HashMap<>();

    private static final int RETRY_OTP_MAX = 5;
    private static final byte[] SIGN = "Qkc9hS0P9cZVMGYTdjLx".getBytes();
    private static final Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private static final String OTP_ID = "id";
    private static final String USER_ID = "principal";

    public OtpProcessor(OtpService service, EmailService emailService, ProfileService profileService, UserService userService, OtpRetryService otpRetryService) {
        this.service = service;
        this.emailService = emailService;
        this.profileService = profileService;
        this.userService = userService;
        this.otpRetryService = otpRetryService;
    }

    @Transactional(rollbackOn = Exception.class)
    public void resetPassword(ResetPasswordModel model) throws InputInvalidException, NotFoundEntityException {
        if (!model.getPassword().equals(model.getRetypePassword())) {
            throw InputInvalidException.of("password.not-matched");
        }

        var claims = Jwts.parser()
                .setSigningKey(SIGN)
                .parseClaimsJws(model.getActiveToken())
                .getBody();

        var otp = service.findById(claims.get(OTP_ID, Long.class));
        var user = userService.getActiveOrElseThrow(claims.get(USER_ID, Long.class));

        var hashedPass = userService.encodePassword(model.getPassword());
        user.setPassword(hashedPass);
        userService.save(user);

        service.delete(otp);
    }

    int OTP_LENGTH = 6;
    int OTP_EXPIRED_MIN = 15;
    public void getOtpResetPassword(String email) throws NotFoundEntityException, ActionNotAllowAttemptException {
        if (isEmpty(email) || email.length() > 1000 || !emailPattern.matcher(email).matches()) {
            throw NotFoundEntityException.of("email.not-found");
        }
        synchronized (this) {
            if (userLock.containsKey(email)) {
                throw ActionNotAllowAttemptException.of("system.processing.requested-before");
            }
            userLock.put(email, this);
        }
        try {
            var profile = profileService.getByEmailOrElseThrow(email);

            var otpRetry = validateRetry(email);

            String code = RandomStringUtils.randomNumeric(OTP_LENGTH);
            Otp otp = new Otp();
            otp.setCode(code);
            otp.setExpired(DateTimeUtils.now().plusMinutes(OTP_EXPIRED_MIN));
            otp.setCreated(DateTimeUtils.now());
            otp.setReceiver(email);
            otp.setType(OtpType.RESET_PASSWORD);
            emailService.sendResetPasswordEmail(
                    otp.getReceiver(),
                    isEmpty(profile.getHoVaTen()) ? otp.getReceiver() : profile.getHoVaTen(),
                    otp.getCode()
            );

            service.save(otp);

            otpRetry.setupExpired();
            otpRetryService.save(otpRetry);
        } finally {
            userLock.remove(email);
        }
    }

    private OtpRetry validateRetry(String email) throws ActionNotAllowAttemptException {
        var otpRetry = otpRetryService.get(email)
                .orElseGet(() -> OtpRetry.init(email));

        if (otpRetry.isNonExpired()) {
            throw ActionNotAllowAttemptException.of("Vui lòng thực hiện chức năng này sau: "
                    + DateTimeUtils.format(otpRetry.getExpired()));
        }

        otpRetry.incrementTimes();
        if (otpRetry.isOverQuota(RETRY_OTP_MAX)) {

            otpRetry.resetTimes();
            otpRetry.setupLockTime();
            otpRetryService.save(otpRetry);

            throw ActionNotAllowAttemptException.of("Bạn đã vượt quá số lần yêu cầu gửi mã xác thực. Vui lòng quay lại sau: "
                    + DateTimeUtils.format(otpRetry.getExpired()));
        }

        return otpRetry;
    }

    public String verifyResetPassword(ActiveByEmailModel model)
            throws ActionNotAllowAttemptException, NotFoundEntityException {

        var otp = verifyOtp(model.getActiveCode(), model.getEmail(), Otp::isResetPasswordType);
        var profile = profileService.getByEmailOrElseThrow(otp.getReceiver());

        return Jwts.builder()
                .setSubject(otp.getReceiver())
                .claim(OTP_ID, otp.getId())
                .claim(USER_ID, profile.getUserId())
                .setIssuedAt(toJavaUtilDate(now()))
                .setExpiration(toJavaUtilDate(otp.getExpired()))
                .signWith(SignatureAlgorithm.HS512, SIGN)
                .compact();
    }

    private Otp verifyOtp(String activeCode, String email, Predicate<Otp> typePredicate)
            throws NotFoundEntityException, ActionNotAllowAttemptException {
        var otp = service.getByCodeOrElseThrow(activeCode);
        if (otp.isExpired()) {
            service.delete(otp);
            throw ActionNotAllowAttemptException.of("active-code.expired");
        }
        if (typePredicate.negate().test(otp)) {
            throw ActionNotAllowAttemptException.of("active-code.invalid");
        }
        if (!otp.isReceiver(email)) {
            throw ActionNotAllowAttemptException.of("otp.wrong");
        }
        return otp;
    }


}
