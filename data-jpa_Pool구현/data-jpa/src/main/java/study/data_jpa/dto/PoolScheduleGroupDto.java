package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PoolScheduleGroupDto {
    private Long poolId;
    private String poolName;
    private List<ScheduleSummaryDto> schedules;
}
