package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.*;
import study.data_jpa.entity.User;
import study.data_jpa.repository.UserRepository;
import study.data_jpa.service.PoolService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pools")
public class PoolController {

    private final PoolService poolService;
    private final UserRepository userRepository;

    // ✅ 나의 스터디 목록 조회
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<MyPoolDto>>> getMyPools(@AuthenticationPrincipal UserDetails userDetails) {
        String studentNumber = userDetails.getUsername();
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        List<MyPoolDto> result = poolService.getMyPools(user.getId());
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "나의 스터디 목록 조회 성공", result));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreatePoolResponseDto>> createPool(@AuthenticationPrincipal UserDetails userDetails,
                                                                         @RequestBody CreatePoolRequestDto dto) {
        String studentNumber = userDetails.getUsername();
        Long poolId = poolService.createPool(studentNumber, dto);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "스터디 그룹 생성 성공", new CreatePoolResponseDto(poolId)));
    }

    // ✅ 스터디 그룹 상세 조회
    @GetMapping("/{poolId}")
    public ResponseEntity<ApiResponse<PoolDetailDto>> getPoolDetail(@PathVariable Long poolId) {
        PoolDetailDto detail = poolService.getPoolDetail(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "스터디 그룹 상세 조회 성공", detail));
    }

    // ✅ 스터디 그룹 구성원 시간표 조회
    @GetMapping("/{poolId}/timetables")
    public ResponseEntity<ApiResponse<List<UserTimetableResponseDto>>> getPoolMemberTimetables(@PathVariable Long poolId) {
        List<UserTimetableResponseDto> result = poolService.getPoolMembersTimetables(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "스터디 그룹 구성원 시간표 조회 성공", result));
    }

    // ✅ 친구 목록 조회 (스터디 초대용)
    @GetMapping("/friends")
    public ResponseEntity<ApiResponse<List<FriendSimpleDto>>> getFriendList(@AuthenticationPrincipal UserDetails userDetails) {
        String studentNumber = userDetails.getUsername();
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        List<FriendSimpleDto> friends = poolService.getFriendList(user.getId());
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "친구 목록 조회 성공", friends));
    }

    // ✅ 노트 등록
    @PostMapping("/{poolId}/notes")
    public ResponseEntity<ApiResponse<Void>> addPoolNote(@PathVariable Long poolId,
                                                         @RequestBody AddPoolNoteRequestDto dto) {
        poolService.addPoolNote(poolId, dto);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "노트 등록 성공", null));
    }

    // ✅ 노트 목록 조회
    @GetMapping("/{poolId}/notes")
    public ResponseEntity<ApiResponse<List<PoolDetailDto.PoolNoteDto>>> getPoolNotes(@PathVariable Long poolId) {
        List<PoolDetailDto.PoolNoteDto> notes = poolService.getPoolNotes(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "노트 목록 조회 성공", notes));
    }

    // ✅ 스터디 그룹 구성원들의 가능한 시간 조회
    @GetMapping("/{poolId}/available-times")
    public ResponseEntity<ApiResponse<AvailableTimesResponseDto>> getAvailableTimes(@PathVariable Long poolId) {
        Map<String, List<String>> result = poolService.getAvailableTimes(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "가능 시간 조회 성공", new AvailableTimesResponseDto(result)));
    }
}
