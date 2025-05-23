package study.data_jpa.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.*;
import study.data_jpa.entity.*;
import study.data_jpa.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PoolService {

    private final PoolRepository poolRepository;
    private final PoolMemberRepository poolMemberRepository;
    private final UserRepository userRepository;
    private final PoolNoteRepository poolNoteRepository;

    public List<MyPoolDto> getMyPools(Long userId) {
        List<Pool> pools = poolRepository.findPoolsByUserId(userId);
        return pools.stream()
                .map(p -> new MyPoolDto(p.getId(), p.getName(), p.getSubject(), p.getDeadline()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createPool(CreatePoolRequestDto dto) {
        Pool pool = new Pool();
        pool.setName(dto.getName());
        pool.setSubject(dto.getSubject());
        pool.setPoolSubject(dto.getPoolSubject());
        pool.setDeadline(dto.getDeadline());
        pool.setCreatedAt(java.time.LocalDateTime.now());

        poolRepository.save(pool);

        List<User> users = userRepository.findAllById(dto.getMemberIds());
        for (User user : users) {
            PoolMember member = new PoolMember();
            member.changeUser(user);
            member.changePool(pool);
            poolMemberRepository.save(member);
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
}