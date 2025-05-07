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
    private List<Long> memberIds; // 함께 등록할 user ID 목록
}