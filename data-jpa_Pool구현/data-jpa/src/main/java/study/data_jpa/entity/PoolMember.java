package study.data_jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PoolMember {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pool_id")
    private Pool pool;

    public void changeUser(User user) {
        this.user = user;
        user.getPoolMemberships().add(this);
    }

    public void changePool(Pool pool) {
        this.pool = pool;
        pool.getMembers().add(this);
    }
}

