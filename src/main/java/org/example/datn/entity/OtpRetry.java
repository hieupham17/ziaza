package org.example.datn.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static org.example.datn.utils.CalendarUtil.DateTimeUtils.now;

@Entity
@Table(name = "otp_retry")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class OtpRetry {
    @Id
    String id;

    Integer times;
    LocalDateTime expired;

    LocalDateTime created;

    public static OtpRetry init(String receiver) {
        var entity = new OtpRetry();
        entity.setId(receiver);
        entity.resetTimes();
        entity.setupExpired();
        entity.setCreated(now());
        return entity;
    }

    public boolean isNonExpired() {
        return expired.isAfter(now().plusSeconds(30));
    }

    public boolean isOverQuota(int max) {
        return times > max;
    }

    public void incrementTimes() {
        times = times + 1;
    }

    public void resetTimes() {
        times = 0;
    }

    public void setupExpired() {
        expired = now().plusSeconds(30);
    }

    public void setupLockTime() {
        expired = now().plusHours(1);
    }
}
