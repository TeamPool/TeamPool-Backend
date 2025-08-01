package study.data_jpa.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.*;
import study.data_jpa.entity.*;
import study.data_jpa.repository.*;
import study.data_jpa.util.TimeRange;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PoolService {

    private final PoolRepository poolRepository;
    private final PoolMemberRepository poolMemberRepository;
    private final UserRepository userRepository;
    private final PoolNoteRepository poolNoteRepository;
    private final PersonalTimetableRepository personalTimetableRepository;

    public List<MyPoolDto> getMyPools(Long userId) {
        List<Pool> pools = poolRepository.findPoolsByUserId(userId);

        return pools.stream()
                .map(pool -> {
                    List<String> memberNicknames = pool.getMembers().stream()
                            .map(pm -> pm.getUser().getNickname())
                            .collect(Collectors.toList());

                    return new MyPoolDto(
                            pool.getId(),
                            pool.getName(),
                            pool.getSubject(),
                            pool.getPoolSubject(),
                            pool.getDeadline(),
                            pool.getCreatedAt(),
                            memberNicknames
                    );
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public Long createPool(String ownerStudentNumber, CreatePoolRequestDto dto) {
        // 1. Pool 객체 생성 및 저장
        Pool pool = new Pool();
        pool.setName(dto.getName());
        pool.setSubject(dto.getSubject());
        pool.setPoolSubject(dto.getPoolSubject());
        pool.setDeadline(dto.getDeadline());
        pool.setCreatedAt(LocalDateTime.now());

        poolRepository.save(pool);

        // 2. 생성자 (owner) 등록
        User owner = userRepository.findByStudentNumber(ownerStudentNumber)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        PoolMember ownerMember = new PoolMember();
        ownerMember.changeUser(owner);
        ownerMember.changePool(pool);
        poolMemberRepository.save(ownerMember);

        // 3. 나머지 멤버들 학번 기준으로 조회 및 등록
        List<String> memberNumbers = dto.getMemberStudentNumbers();
        for (String number : memberNumbers) {
            if (!number.equals(ownerStudentNumber)) { // 중복 등록 방지
                User member = userRepository.findByStudentNumber(number)
                        .orElseThrow(() -> new RuntimeException("학번 '" + number + "'에 해당하는 사용자를 찾을 수 없습니다."));
                PoolMember memberEntry = new PoolMember();
                memberEntry.changeUser(member);
                memberEntry.changePool(pool);
                poolMemberRepository.save(memberEntry);
            }
        }

        return pool.getId();
    }


    public PoolDetailDto getPoolDetail(Long poolId) {
        Pool pool = poolRepository.findDetailedPoolById(poolId)
                .orElseThrow(() -> new IllegalArgumentException("풀을 찾을 수 없습니다."));

        List<PoolDetailDto.PoolMemberDto> memberDtos = pool.getMembers().stream()
                .map(m -> new PoolDetailDto.PoolMemberDto(m.getUser().getId(), m.getUser().getNickname()))
                .collect(Collectors.toList());

        List<PoolDetailDto.PoolNoteDto> noteDtos = pool.getNotes().stream()
                .map(n -> new PoolDetailDto.PoolNoteDto(n.getTitle(), n.getSummary(), n.getTime()))
                .collect(Collectors.toList());

        List<ScheduleSummaryDto> scheduleDtos = pool.getSchedules().stream()
                .map(s -> new ScheduleSummaryDto(s.getId(), s.getTitle(), s.getStartDatetime(), s.getEndDatetime(), s.getPlace()))
                .collect(Collectors.toList());

        PoolDetailDto detailDto = new PoolDetailDto();
        detailDto.setPoolId(pool.getId());
        detailDto.setName(pool.getName());
        detailDto.setSubject(pool.getSubject());
        detailDto.setPoolSubject(pool.getPoolSubject());
        detailDto.setDeadline(pool.getDeadline());
        detailDto.setCreatedAt(pool.getCreatedAt());
        detailDto.setMembers(memberDtos);
        detailDto.setNotes(noteDtos);
        detailDto.setSchedules(scheduleDtos);

        return detailDto;
    }

    public List<UserTimetableResponseDto> getPoolMembersTimetables(Long poolId) {
        Pool pool = poolRepository.findById(poolId)
                .orElseThrow(() -> new EntityNotFoundException("Pool not found"));

        List<UserTimetableResponseDto> result = new ArrayList<>();

        for (PoolMember member : pool.getMembers()) {
            User user = member.getUser();

            List<TimetableRequestDto> timetables = user.getTimetables().stream()
                    .map(t -> {
                        TimetableRequestDto dto = new TimetableRequestDto();
                        dto.setSubject(t.getSubject());
                        dto.setDayOfWeek(t.getDayOfWeek());
                        dto.setStartTime(t.getStartTime());
                        dto.setEndTime(t.getEndTime());
                        dto.setPlace(t.getPlace());
                        return dto;
                    })
                    .collect(Collectors.toList());

            result.add(new UserTimetableResponseDto(user.getId(), user.getNickname(), timetables));
        }

        return result;
    }

    public List<FriendSimpleDto> getFriendList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return user.getFriends().stream()
                .map(friend -> {
                    User friendUser = friend.getFriendUser();
                    return new FriendSimpleDto(
                            friendUser.getId(),
                            friendUser.getStudentNumber(),
                            friendUser.getNickname()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void addPoolNote(Long poolId, AddPoolNoteRequestDto dto) {
        Pool pool = poolRepository.findById(poolId)
                .orElseThrow(() -> new IllegalArgumentException("풀을 찾을 수 없습니다."));

        PoolNote note = new PoolNote();
        note.setTitle(dto.getTitle());
        note.setSummary(dto.getSummary());
        note.setTime(dto.getTime());
        note.changePool(pool); // 연관관계 설정

        poolNoteRepository.save(note);
    }

    public List<PoolDetailDto.PoolNoteDto> getPoolNotes(Long poolId) {
        Pool pool = poolRepository.findById(poolId)
                .orElseThrow(() -> new IllegalArgumentException("풀을 찾을 수 없습니다."));

        return pool.getNotes().stream()
                .map(n -> new PoolDetailDto.PoolNoteDto(n.getTitle(), n.getSummary(), n.getTime()))
                .collect(Collectors.toList());
    }

    public Map<String, List<String>> getAvailableTimes(Long poolId) {
        List<User> members = poolMemberRepository.findUsersByPoolId(poolId);

        Map<DayOfWeek, List<TimeRange>> busyMap = new HashMap<>();

        for (User user : members) {
            List<PersonalTimetable> timetables = personalTimetableRepository.findByUserId(user.getId());
            for (PersonalTimetable t : timetables) {
                busyMap.computeIfAbsent(t.getDayOfWeek(), k -> new ArrayList<>())
                        .add(new TimeRange(t.getStartTime(), t.getEndTime()));
            }
        }

        Map<String, List<String>> availableMap = new LinkedHashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            List<TimeRange> busy = busyMap.getOrDefault(day, new ArrayList<>());
            List<TimeRange> available = calculateAvailableRanges(busy);

            if (available.isEmpty()) {
                availableMap.put(day.name(), List.of("회의 가능한 시간이 없습니다"));
            } else {
                availableMap.put(day.name(), available.stream()
                        .map(TimeRange::toString)
                        .collect(Collectors.toList()));
            }
        }

        return availableMap;
    }

    private List<TimeRange> calculateAvailableRanges(List<TimeRange> busyRanges) {
        List<TimeRange> merged = mergeOverlapping(busyRanges);
        List<TimeRange> available = new ArrayList<>();

        LocalTime dayStart = LocalTime.of(0, 0);
        LocalTime dayEnd = LocalTime.of(23, 59);

        for (TimeRange busy : merged) {
            if (dayStart.isBefore(busy.getStart())) {
                available.add(new TimeRange(dayStart, busy.getStart()));
            }
            dayStart = busy.getEnd();
        }

        if (dayStart.isBefore(dayEnd)) {
            available.add(new TimeRange(dayStart, dayEnd));
        }

        return available;
    }

    private List<TimeRange> mergeOverlapping(List<TimeRange> ranges) {
        if (ranges.isEmpty()) return List.of();

        List<TimeRange> sorted = new ArrayList<>(ranges);
        sorted.sort(Comparator.naturalOrder());

        List<TimeRange> merged = new ArrayList<>();
        TimeRange prev = sorted.get(0);

        for (int i = 1; i < sorted.size(); i++) {
            TimeRange curr = sorted.get(i);
            if (prev.overlaps(curr) || prev.isAdjacent(curr)) {
                prev = prev.merge(curr);
            } else {
                merged.add(prev);
                prev = curr;
            }
        }

        merged.add(prev);
        return merged;
    }

}