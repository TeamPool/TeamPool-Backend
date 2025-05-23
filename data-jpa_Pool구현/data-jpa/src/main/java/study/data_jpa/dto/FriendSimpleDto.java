package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendSimpleDto {
    private Long id;
    private String studentNumber;
    private String nickname;
}
