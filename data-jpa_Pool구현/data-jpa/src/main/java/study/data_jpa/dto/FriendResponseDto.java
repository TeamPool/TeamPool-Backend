package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor  // 모든 필드에 대한 생성자 자동 생성
public class FriendResponseDto {
    private Long friendId;
    private String nickname;
    private String studentNumber;
}
