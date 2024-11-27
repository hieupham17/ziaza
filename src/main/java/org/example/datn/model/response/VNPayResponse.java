package org.example.datn.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class VNPayResponse {
    public String code;
    public String message;
    public String paymentUrl;
}
