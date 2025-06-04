package study.data_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MyPoolDto {
    private Long poolId;
    private String name;
    private String subject;
    private String poolSubject;
    private LocalDate deadline;
    private LocalDateTime createdAt;
    private List<String> members;

    public MyPoolDto(Long poolId, String name, String subject, String poolSubject,
                     LocalDate deadline, LocalDateTime createdAt, List<String> members) {
        this.poolId = poolId;
        this.name = name;
        this.subject = subject;
        this.poolSubject = poolSubject;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.members = members;
    }
}
