package study.data_jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.AddScheduleRequestDto;
import study.data_jpa.dto.ScheduleSummaryDto;
import study.data_jpa.entity.Pool;
import study.data_jpa.entity.PoolSchedule;
import study.data_jpa.repository.PoolRepository;
import study.data_jpa.repository.PoolScheduleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final PoolScheduleRepository scheduleRepository;
    private final PoolRepository poolRepository;

    public List<ScheduleSummaryDto> getAllSchedules(Long poolId) {
        return scheduleRepository.findByPool_Id(poolId)
                .stream()
                .map(s -> new ScheduleSummaryDto(
                        s.getId(),
                        s.getTitle(),
                        s.getStartDatetime(),
                        s.getEndDatetime(),
                        s.getPlace()))
                .collect(Collectors.toList());
    }

    public List<ScheduleSummaryDto> getSchedulesByDay(LocalDate date) {
        return scheduleRepository.findByDate(date)
                .stream()
                .map(s -> new ScheduleSummaryDto(
                        s.getId(),
                        s.getTitle(),
                        s.getStartDatetime(),
                        s.getEndDatetime(),
                        s.getPlace()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addSchedule(AddScheduleRequestDto dto) {
        Pool pool = poolRepository.findById(dto.getPoolId())
                .orElseThrow(() -> new IllegalArgumentException("해당 Pool이 존재하지 않습니다."));

        PoolSchedule schedule = new PoolSchedule();
        schedule.setTitle(dto.getTitle());
        schedule.setStartDatetime(dto.getStartDatetime());
        schedule.setEndDatetime(dto.getEndDatetime());
        schedule.setPlace(dto.getPlace());
        schedule.changePool(pool);

        scheduleRepository.save(schedule);
    }
}
