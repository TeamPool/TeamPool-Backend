package study.data_jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.*;
import study.data_jpa.entity.*;
import study.data_jpa.repository.*;

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
}

