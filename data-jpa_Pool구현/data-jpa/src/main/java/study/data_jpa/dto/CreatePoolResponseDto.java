package study.data_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreatePoolResponseDto {
    private Long poolId;

    public CreatePoolResponseDto(Long poolId) {
        this.poolId = poolId;
    }
}

