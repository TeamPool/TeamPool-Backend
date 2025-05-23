package study.data_jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.TimetableRequestDto;
import study.data_jpa.entity.PersonalTimetable;
import study.data_jpa.entity.User;
import study.data_jpa.repository.PersonalTimetableRepository;
import study.data_jpa.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TimetableService {

    private final PersonalTimetableRepository timetableRepository;
    private final UserRepository userRepository;

    public void saveTimetable(Long userId, TimetableRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        PersonalTimetable timetable = new PersonalTimetable();
        timetable.setDayOfWeek(dto.getDayOfWeek());
        timetable.setSubject(dto.getSubject());
        timetable.setStartTime(dto.getStartTime());
        timetable.setEndTime(dto.getEndTime());
        timetable.setPlace(dto.getPlace());

        timetable.changeUser(user);

        timetableRepository.save(timetable);
    }

    public void saveAllTimetables(Long userId, List<TimetableRequestDto> dtos) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        for (TimetableRequestDto dto : dtos) {
            PersonalTimetable timetable = new PersonalTimetable();
            timetable.setDayOfWeek(dto.getDayOfWeek());
            timetable.setSubject(dto.getSubject());
            timetable.setStartTime(dto.getStartTime());
            timetable.setEndTime(dto.getEndTime());
            timetable.setPlace(dto.getPlace());

            timetable.changeUser(user);

            timetableRepository.save(timetable);
        }
    }
}
