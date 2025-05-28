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
    public ResponseEntity<List<MyPoolDto>> getMyPools(@RequestParam Long userId) {
        return ResponseEntity.ok(poolService.getMyPools(userId));
    }

    // POST /api/pool
    @PostMapping
    public ResponseEntity<CreatePoolResponseDto> createPool(@RequestBody CreatePoolRequestDto dto) {
        Long poolId = poolService.createPool(dto);
        return ResponseEntity.ok(new CreatePoolResponseDto(poolId));
    }

    // GET /api/pools/{poolId}
    @GetMapping("/{poolId}")
    public ResponseEntity<PoolDetailDto> getPoolDetail(@PathVariable Long poolId) {
        return ResponseEntity.ok(poolService.getPoolDetail(poolId));
    }

    @GetMapping("/{poolId}/timetables")
    public ResponseEntity<List<UserTimetableResponseDto>> getPoolMemberTimetables(@PathVariable Long poolId) {
        return ResponseEntity.ok(poolService.getPoolMembersTimetables(poolId));
    }

    @GetMapping("/friends")
    public ResponseEntity<List<FriendSimpleDto>> getFriendList(@RequestParam Long userId) {
        return ResponseEntity.ok(poolService.getFriendList(userId));
    }

    @PostMapping("/{poolId}/notes")
    public ResponseEntity<Void> addPoolNote(@PathVariable Long poolId,
                                            @RequestBody AddPoolNoteRequestDto dto) {
        poolService.addPoolNote(poolId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{poolId}/notes")
    public ResponseEntity<List<PoolDetailDto.PoolNoteDto>> getPoolNotes(@PathVariable Long poolId) {
        return ResponseEntity.ok(poolService.getPoolNotes(poolId));
    }

    @GetMapping("/api/pools/{poolId}/available-times")
    public ResponseEntity<AvailableTimesResponseDto> getAvailableTimes(@PathVariable Long poolId) {
        Map<String, List<String>> result = poolService.getAvailableTimes(poolId);
        return ResponseEntity.ok(new AvailableTimesResponseDto(result));
    }
}