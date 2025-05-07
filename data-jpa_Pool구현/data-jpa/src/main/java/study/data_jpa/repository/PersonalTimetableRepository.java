package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entity.PersonalTimetable;

public interface PersonalTimetableRepository extends JpaRepository<PersonalTimetable, Long> {}