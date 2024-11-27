package org.example.datn.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.OtpType;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.example.datn.utils.CalendarUtil.DateTimeUtils.now;

@Entity
@Table(name = "otp")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String code;
    String receiver;
    @Enumerated(EnumType.STRING)
    OtpType type;
    LocalDateTime expired;

    LocalDateTime created;

    public boolean isReceiver(String receiver) {
        return this.receiver.equals(receiver);
    }

    public boolean isExpired() {
        return expired.isBefore(now());
    }

    public boolean isVerifyAccountType() {
        return isType(OtpType.VERIFY_ACCOUNT);
    }

    public boolean isResetPasswordType() {
        return isType(OtpType.RESET_PASSWORD);
    }

    private boolean isType(OtpType type) {
        return this.type == type;
    }
}
