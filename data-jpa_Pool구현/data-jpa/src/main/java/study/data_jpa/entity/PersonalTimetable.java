package study.data_jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PersonalTimetable {
    @Id
    @GeneratedValue
    @Column(name = "timetable_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private String subject;

    private LocalTime startTime;

    private LocalTime endTime;

    private String place;

    public void changeUser(User user) {
        this.user = user;
        user.getTimetables().add(this);
    }
}
