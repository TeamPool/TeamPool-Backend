package study.data_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MyPoolDto {
    private Long poolId;
    private String name;
    private String subject;
    private LocalDate deadline;

    public MyPoolDto(Long poolId, String name, String subject, LocalDate deadline) {
        this.poolId = poolId;
        this.name = name;
        this.subject = subject;
        this.deadline = deadline;
    }
}

