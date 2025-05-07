package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.AddScheduleRequestDto;
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
    public ResponseEntity<List<ScheduleSummaryDto>> getAllSchedules(@RequestParam Long poolId) {
        return ResponseEntity.ok(scheduleService.getAllSchedules(poolId));
    }

    // GET /api/schedules/by-day?date=2025-05-08
    @GetMapping("/by-day")
    public ResponseEntity<List<ScheduleSummaryDto>> getSchedulesByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(scheduleService.getSchedulesByDay(date));
    }

    // POST /api/schedules
    @PostMapping
    public ResponseEntity<Void> addSchedule(@RequestBody AddScheduleRequestDto dto) {
        scheduleService.addSchedule(dto);
        return ResponseEntity.ok().build();
    }
}
