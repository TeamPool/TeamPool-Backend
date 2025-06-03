package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class NicknameResponseDto {
    private String studentNumber;
    private String nickname;

    public NicknameResponseDto(String studentNumber, String nickname) {
        this.studentNumber = studentNumber;
        this.nickname = nickname;
    }

    // Getter
    public String getStudentNumber() {
        return studentNumber;
    }

    public String getNickname() {
        return nickname;
    }
}
