package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.*;
import study.data_jpa.entity.User;
import study.data_jpa.repository.UserRepository;
import study.data_jpa.service.ScheduleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserRepository userRepository;

    // ✅ 특정 스터디의 전체 일정 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleSummaryDto>>> getAllSchedules(@RequestParam Long poolId) {
        List<ScheduleSummaryDto> result = scheduleService.getAllSchedules(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "스터디 일정 전체 조회 성공", result));
    }

    // ✅ 특정 날짜의 전체 일정 조회 (스터디 구분 없음)
    @GetMapping("/by-day")
    public ResponseEntity<ApiResponse<List<ScheduleSummaryDto>>> getSchedulesByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ScheduleSummaryDto> result = scheduleService.getSchedulesByDay(date);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "특정 날짜 일정 조회 성공", result));
    }

    // ✅ 내가 속한 모든 스터디 일정 조회
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<PoolScheduleGroupDto>>> getMyAllSchedules(
            @AuthenticationPrincipal UserDetails userDetails) {

        String studentNumber = userDetails.getUsername();
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        List<PoolScheduleGroupDto> result = scheduleService.getMyAllSchedules(user.getId());
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "내 전체 스터디 일정 조회 성공", result));
    }

    // ✅ 일정 등록
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addSchedule(@RequestBody AddScheduleRequestDto dto) {
        scheduleService.addSchedule(dto);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "일정 등록 성공", null));
    }
}