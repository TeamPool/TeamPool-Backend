package study.data_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class AddPoolNoteRequestDto {
    private String title;
    private String summary;
    private LocalTime time;
}
