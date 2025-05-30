package study.data_jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String studentNumber;
    private String password;
}
