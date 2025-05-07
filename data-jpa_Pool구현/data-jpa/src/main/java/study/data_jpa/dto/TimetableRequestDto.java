package study.data_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class TimetableRequestDto {
    private DayOfWeek dayOfWeek;
    private String subject;
    private LocalTime startTime;
    private LocalTime endTime;
    private String place;
}
