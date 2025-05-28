package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;    // "SUCCESS" or "FAIL"
    private String message;
    private T data;
}
