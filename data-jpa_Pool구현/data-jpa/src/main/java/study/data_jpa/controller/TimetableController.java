package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.TimetableRequestDto;
import study.data_jpa.service.TimetableService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timetables")
public class TimetableController {

    private final TimetableService timetableService;

    // POST /api/timetables/users/1
    @PostMapping("/users/{userId}")
    public ResponseEntity<Void> saveTimetables(@PathVariable Long userId,
                                               @RequestBody List<TimetableRequestDto> dtos) {
        timetableService.saveAllTimetables(userId, dtos);
        return ResponseEntity.status(201).build(); // 201 Created
    }
}