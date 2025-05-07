package study.data_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AddScheduleRequestDto {
    private Long poolId;
    private String title;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private String place;
}
