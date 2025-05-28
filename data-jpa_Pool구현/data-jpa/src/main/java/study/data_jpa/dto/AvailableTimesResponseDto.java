package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class AvailableTimesResponseDto {
    private Map<String, List<String>> availableTimes;
}
