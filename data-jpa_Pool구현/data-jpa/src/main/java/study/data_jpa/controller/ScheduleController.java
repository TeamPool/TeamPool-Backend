package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.AddScheduleRequestDto;
import study.data_jpa.dto.ApiResponse;
import study.data_jpa.dto.ScheduleSummaryDto;
import study.data_jpa.service.ScheduleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // GET /api/schedules?poolId=3
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleSummaryDto>>> getAllSchedules(@RequestParam Long poolId) {
        List<ScheduleSummaryDto> result = scheduleService.getAllSchedules(poolId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "스터디 일정 전체 조회 성공", result));
    }

    // GET /api/schedules/by-day?date=2025-05-08
    @GetMapping("/by-day")
    public ResponseEntity<ApiResponse<List<ScheduleSummaryDto>>> getSchedulesByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ScheduleSummaryDto> result = scheduleService.getSchedulesByDay(date);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "특정 날짜 일정 조회 성공", result));
    }

    // POST /api/schedules
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addSchedule(@RequestBody AddScheduleRequestDto dto) {
        scheduleService.addSchedule(dto);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "일정 등록 성공", null));
    }
}
