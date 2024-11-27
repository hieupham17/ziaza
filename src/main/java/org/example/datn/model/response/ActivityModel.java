package org.example.datn.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.ActivityType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityModel implements Serializable {


    private static final long serialVersionUID = 1L;

    Long id;

    Long userId;
    String username;

    String method;
    String action;
    String path;
    ActivityType activityType;



    LocalDateTime ngayTao;
    LocalDateTime ngayCapNhat;
    String nguoiTao;
    String nguoiCapNhat;
}
