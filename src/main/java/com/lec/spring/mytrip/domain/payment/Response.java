package com.lec.spring.mytrip.domain.payment;

import com.lec.spring.mytrip.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
    Payment payment;
    Map<String, Object> response;
}
