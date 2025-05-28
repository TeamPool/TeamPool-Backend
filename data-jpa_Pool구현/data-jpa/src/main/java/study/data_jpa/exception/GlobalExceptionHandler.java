package study.data_jpa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.data_jpa.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 일반 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("FAIL", "서버 오류: " + ex.getMessage(), null));
    }

    // 잘못된 요청
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("FAIL", ex.getMessage(), null));
    }

    // 필요한 경우 추가 예외 핸들러 확장 가능
}



