package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserTimetableResponseDto {
    private Long userId;
    private String nickname;
    private List<TimetableRequestDto> timetables;
}
