package org.example.datn.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult<T> {
    private Object data;
    private List<T> listData;
    private String message;
    private Integer totalPage;
    private Integer currentPage;
    private String code;
    private Long totalRecord;

    {
        this.message = "Thành công";
        this.code = "200";
    }

    public ServiceResult(Object data, Integer totalPage, Integer currentPage) {
        this.data = data;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    public ServiceResult(Object data, Integer totalPage, Integer currentPage, Long totalRecord) {
        this.data = data;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.totalRecord = totalRecord;
    }

    public ServiceResult(Object data, Integer totalPage, Integer currentPage, String message) {
        this.data = data;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.message = message;
    }

    public ServiceResult(Object data, Integer totalPage, Integer currentPage, Long totalRecord, String message, String code) {
        this.data = data;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.totalRecord = totalRecord;
        this.message = message;
        this.code = code;
    }

    public ServiceResult(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public ServiceResult(String message, String code) {
        this.code = code;
        this.message = message;
    }

    public ServiceResult(String message) {
        this.message = message;
    }

    public ServiceResult(Object data, String message, String code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public ServiceResult(Object data, List<T> listData, String message) {
        this.data = data;
        this.listData = listData;
        this.message = message;
    }
}
