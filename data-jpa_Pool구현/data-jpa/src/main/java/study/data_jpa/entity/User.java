package study.data_jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String studentNumber;

    private String nickname;

    private String password;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PersonalTimetable> timetables = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PoolMember> poolMemberships = new ArrayList<>();
}