// UserSearchResponseDto.java
package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSearchResponseDto {
    private String studentNumber;
    private String nickname;
}
