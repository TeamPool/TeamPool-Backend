package study.data_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PoolDetailDto {
    private Long poolId;
    private String name;
    private String subject;
    private String poolSubject;
    private LocalDate deadline;
    private LocalDateTime createdAt;

    private List<PoolMemberDto> members;
    private List<ScheduleSummaryDto> schedules;
    private List<PoolNoteDto> notes;

    // 내부 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PoolMemberDto {
        private Long userId;
        private String nickname;

        public PoolMemberDto(Long userId, String nickname) {
            this.userId = userId;
            this.nickname = nickname;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PoolNoteDto {
        private String title;
        private String summary;
        private LocalTime time;

        public PoolNoteDto(String title, String summary, LocalTime time) {
            this.title = title;
            this.summary = summary;
            this.time = time;
        }
    }
}
