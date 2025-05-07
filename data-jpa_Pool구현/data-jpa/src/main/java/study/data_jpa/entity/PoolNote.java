package study.data_jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PoolNote {

    @Id
    @Column(name = "poolnote_id")
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "pool_id")
    private Pool pool;

    private String title;

    private String summary;

    private LocalTime time;

    public void changePool(Pool pool) {
        this.pool = pool;
        pool.getNotes().add(this);
    }

}

