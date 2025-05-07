package study.data_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleSummaryDto {
    private Long scheduleId;
    private String title;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private String place;

    public ScheduleSummaryDto(Long scheduleId, String title, LocalDateTime start, LocalDateTime end, String place) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.startDatetime = start;
        this.endDatetime = end;
        this.place = place;
    }
}

