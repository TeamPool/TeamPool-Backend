package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.TimetableRequestDto;
import study.data_jpa.service.TimetableService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timetables")
public class TimetableController {

    private final TimetableService timetableService;

    // POST /api/timetables?userId=1
    @PostMapping
    public ResponseEntity<Void> saveTimetable(@RequestParam Long userId,
                                              @RequestBody TimetableRequestDto dto) {
        timetableService.saveTimetable(userId, dto);
        return ResponseEntity.ok().build();
    }
}
