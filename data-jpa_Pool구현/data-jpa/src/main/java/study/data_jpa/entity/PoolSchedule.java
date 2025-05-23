package study.data_jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PoolSchedule {

    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pool_id")
    private Pool pool;

    private String title;

    private LocalDateTime startDatetime;

    private LocalDateTime endDatetime;

    private String place;

    public void changePool(Pool pool) {
        this.pool = pool;
        pool.getSchedules().add(this);
    }
}

