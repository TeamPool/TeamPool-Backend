package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.*;
import study.data_jpa.service.PoolService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pools")
public class PoolController {

    private final PoolService poolService;

    // GET /api/pools/my?userId=1
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<MyPoolDto>>> getMyPools(@RequestParam Long userId) {
        List<MyPoolDto> result = poolService.getMyPools(userId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "나의 스터디 목록 조회 성공", result));
    }

    // POST /api/pools
    @PostMapping
    public ResponseEntity<ApiResponse<CreatePoolResponseDto>> createPool(@RequestBody CreatePoolRequestDto dto) {
        Long poolId = poolService.createPool(dto);
        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "스터디 그룹 생성 성공", new CreatePoolResponseDto(poolId))
        );
    }

    // GET /api/pools/{poolId}
    @GetMapping("/{poolId}")
    public ResponseEntity<ApiResponse<PoolDetailDto>> getPoolDetail(@PathVariable Long poolId) {
        PoolDetailDto detail = poolService.getPoolDetail(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "스터디 그룹 상세 조회 성공", detail));
    }

    // GET /api/pools/{poolId}/timetables
    @GetMapping("/{poolId}/timetables")
    public ResponseEntity<ApiResponse<List<UserTimetableResponseDto>>> getPoolMemberTimetables(@PathVariable Long poolId) {
        List<UserTimetableResponseDto> result = poolService.getPoolMembersTimetables(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "스터디 그룹 구성원 시간표 조회 성공", result));
    }

    // GET /api/pools/friends?userId=1
    @GetMapping("/friends")
    public ResponseEntity<ApiResponse<List<FriendSimpleDto>>> getFriendList(@RequestParam Long userId) {
        List<FriendSimpleDto> friends = poolService.getFriendList(userId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "친구 목록 조회 성공", friends));
    }

    // POST /api/pools/{poolId}/notes
    @PostMapping("/{poolId}/notes")
    public ResponseEntity<ApiResponse<Void>> addPoolNote(@PathVariable Long poolId,
                                                         @RequestBody AddPoolNoteRequestDto dto) {
        poolService.addPoolNote(poolId, dto);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "노트 등록 성공", null));
    }

    // GET /api/pools/{poolId}/notes
    @GetMapping("/{poolId}/notes")
    public ResponseEntity<ApiResponse<List<PoolDetailDto.PoolNoteDto>>> getPoolNotes(@PathVariable Long poolId) {
        List<PoolDetailDto.PoolNoteDto> notes = poolService.getPoolNotes(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "노트 목록 조회 성공", notes));
    }

    // GET /api/pools/{poolId}/available-times
    @GetMapping("/{poolId}/available-times")
    public ResponseEntity<ApiResponse<AvailableTimesResponseDto>> getAvailableTimes(@PathVariable Long poolId) {
        Map<String, List<String>> result = poolService.getAvailableTimes(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "가능 시간 조회 성공", new AvailableTimesResponseDto(result)));
    }
}
