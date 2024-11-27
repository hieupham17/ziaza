package org.example.datn.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringEscapeUtils;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.ProfileRequest;
import org.example.datn.service.ProfileService;
import org.example.datn.service.UserService;
import org.example.datn.transformer.ProfileTransformer;
import org.example.datn.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * @author hoangKhong
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileProcessor {

    ProfileService profileService;
    UserService userService;
    ProfileTransformer transformer;

    public ProfileProcessor(ProfileService profileService, UserService userService, ProfileTransformer profileTransformer) {
        this.profileService = profileService;
        this.userService = userService;
        this.transformer = profileTransformer;
    }
    private LocalDate parseOrNull(String date) {
        return (date != null && !date.isEmpty()) ? LocalDate.parse(date) : null;
    }
    private static final String UPLOAD_DIR = "images";
    @Transactional(rollbackOn = Exception.class)
    public ServiceResult update(String request, MultipartFile file, UserAuthentication ua) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        JsonSerializer<LocalDate> serializer = (src, typeOfSrc, context) ->
                context.serialize(src.format(formatter));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, serializer)
                .create();
        ProfileRequest target = gson.fromJson(removeQuotesAndUnescape(request), ProfileRequest.class);
        Long userId = ua.getPrincipal();

        var user = userService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        var profile = profileService.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found for user ID: " + userId));

        // Cập nhật thông tin cá nhân
        profile.setHoVaTen(target.getHoVaTen());
        profile.setEmail(target.getEmail());
        profile.setPhone(target.getPhone());
        profile.setCccd(target.getCccd());
        profile.setGioiTinh(target.getGioiTinh());
        profile.setNgaySinh(parseOrNull(target.getNgaySinh()));
        profile.setNgayCapNhat(LocalDateTime.now());
        // Nếu có file ảnh avatar thì xử lý upload
        if (file != null && !file.isEmpty()) {
            var url = saveImage(file);
            profile.setAvatar(url);
        }
        // Lưu lại profile
        profileService.save(profile);

        // Nếu email đã thay đổi, cập nhật tên đăng nhập của user
        if (!user.getUserName().equals(profile.getEmail())) {
            user.setUserName(profile.getEmail());
            userService.save(user);
        }

        return new ServiceResult();
    }
    private String saveImage(MultipartFile image) {
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        String imagePath = UPLOAD_DIR + File.separator + fileName;

        try {
            // Kiểm tra và tạo thư mục UPLOAD_DIR nếu nó chưa tồn tại
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // Lưu hình ảnh
            byte[] bytes = image.getBytes();
            Path path = Paths.get(imagePath);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể lưu hình ảnh!");
        }

        return imagePath;
    }


    public String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

}
