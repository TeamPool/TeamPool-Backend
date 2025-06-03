package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 모든 필드 생성자
public class FriendRequestDto {
    private String studentNumber; // 친구로 추가할 사용자의 학번
}
//test
