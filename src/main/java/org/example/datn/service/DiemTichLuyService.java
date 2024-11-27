package org.example.datn.service;

import org.example.datn.entity.DiemTichLuy;
import org.example.datn.repository.DiemTichLuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiemTichLuyService {
    @Autowired
    private DiemTichLuyRepository diemTichLuyRepository;

    // Thêm điểm tích lũy
    public DiemTichLuy update(Long idNguoiDung, int diem) {
        // Truy vấn tổng điểm hiện tại của người dùng
        Integer currentDiem = diemTichLuyRepository.findSumDiemByIdNguoiDung(idNguoiDung);

        // Nếu không có điểm tích lũy, khởi tạo điểm mặc định là 0
        if (currentDiem == null) {
            currentDiem = 0;
        }

        // Cộng thêm điểm vào tổng điểm hiện tại
        currentDiem += diem;

        // Kiểm tra xem người dùng đã có bản ghi DiemTichLuy hay chưa
        Optional<DiemTichLuy> existingDiemTichLuy = diemTichLuyRepository.findByIdNguoiDung(idNguoiDung);

        DiemTichLuy diemTichLuy;

        if (existingDiemTichLuy.isPresent()) {
            // Nếu bản ghi tồn tại, cập nhật điểm (chỉ cập nhật điểm cho bản ghi hiện tại)
            diemTichLuy = existingDiemTichLuy.get();
            diemTichLuy.setDiem(currentDiem); // Cập nhật điểm mới
//            diemTichLuy.setLyDo(lyDo); // Cập nhật lý do
//            diemTichLuy.setNguoiTao(nguoiTao); // Cập nhật người tạo
            diemTichLuy.setNgayCapNhat(LocalDateTime.now()); // Cập nhật ngày cập nhật
        } else {
            // Nếu chưa có điểm, tạo mới bản ghi DiemTichLuy
            diemTichLuy = new DiemTichLuy();
            diemTichLuy.setIdNguoiDung(idNguoiDung);
            diemTichLuy.setDiem(currentDiem); // Lưu điểm mới
//            diemTichLuy.setLyDo(lyDo);
//            diemTichLuy.setNguoiTao(nguoiTao);
            diemTichLuy.setNgayTao(LocalDateTime.now()); // Cập nhật ngày tạo
            diemTichLuy.setNgayCapNhat(LocalDateTime.now()); // Cập nhật ngày cập nhật
        }

        // Lưu đối tượng DiemTichLuy vào cơ sở dữ liệu (có thể là update hoặc insert)
        return diemTichLuyRepository.save(diemTichLuy);
    }

    public DiemTichLuy useDiem(Long idNguoiDung, int diemCanSuDung) {
        // Truy vấn tổng điểm hiện tại của người dùng
        Integer currentDiem = diemTichLuyRepository.findSumDiemByIdNguoiDung(idNguoiDung);

        // Nếu không có điểm tích lũy hoặc không đủ điểm
        if (currentDiem == null || currentDiem < diemCanSuDung) {
            throw new IllegalArgumentException("Không đủ điểm để sử dụng.");
        }

        // Trừ số điểm cần sử dụng
        currentDiem -= diemCanSuDung;

        // Lấy bản ghi hiện tại của người dùng
        Optional<DiemTichLuy> existingDiemTichLuy = diemTichLuyRepository.findByIdNguoiDung(idNguoiDung);

        DiemTichLuy diemTichLuy;

        if (existingDiemTichLuy.isPresent()) {
            // Nếu bản ghi tồn tại, cập nhật điểm
            diemTichLuy = existingDiemTichLuy.get();
            diemTichLuy.setDiem(currentDiem); // Cập nhật điểm sau khi sử dụng
//            diemTichLuy.setLyDo(lyDo); // Lý do trừ điểm
//            diemTichLuy.setNguoiCapNhat(nguoiTao); // Cập nhật người thực hiện
            diemTichLuy.setNgayCapNhat(LocalDateTime.now()); // Cập nhật thời gian
        } else {
            // Nếu chưa có điểm, đây là lỗi logic
            throw new IllegalStateException("Không tìm thấy bản ghi điểm tích lũy.");
        }

        // Lưu đối tượng DiemTichLuy vào cơ sở dữ liệu
        return diemTichLuyRepository.save(diemTichLuy);
    }
}
