package study.data_jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pool {

    @Id
    @GeneratedValue
    @Column(name = "pool_id")
    private Long id;

    private String name;
    private String subject;

    private String poolSubject;

    private LocalDate deadline;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "pool")
    private List<PoolMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "pool")
    private List<PoolSchedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "pool")
    private List<PoolNote> notes = new ArrayList<>();
}

