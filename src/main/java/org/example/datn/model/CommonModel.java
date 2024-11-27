package org.example.datn.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author hoangKhong
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonModel implements Serializable {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime ngayTao;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime ngayCapNhat;
    Long nguoiTao;
    Long nguoiCapNhat;
}
