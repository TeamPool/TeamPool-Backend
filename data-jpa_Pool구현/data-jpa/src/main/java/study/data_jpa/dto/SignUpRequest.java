package study.data_jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    private String studentNumber;  // 학번
    private String nickname;       // 닉네임
    private String password;       // 비밀번호
}
