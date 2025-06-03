package study.data_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreatePoolRequestDto {
    private String name;
    private String subject;
    private String poolSubject;
    private LocalDate deadline;
    private List<String> memberStudentNumbers; // userId → studentNumber로 변경
}