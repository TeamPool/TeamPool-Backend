package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.ApiResponse;
import study.data_jpa.dto.TimetableRequestDto;
import study.data_jpa.entity.User;
import study.data_jpa.repository.UserRepository;
import study.data_jpa.service.TimetableService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timetables")
public class TimetableController {

    private final TimetableService timetableService;
    private final UserRepository userRepository;

    // ✅ 개인 시간표 등록 (토큰 기반 사용자 식별)
    @PostMapping("/me")
    public ResponseEntity<ApiResponse<Void>> saveTimetables(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody List<TimetableRequestDto> dtos) {
        String studentNumber = userDetails.getUsername();
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        timetableService.saveAllTimetables(user.getId(), dtos);

        return ResponseEntity.status(201)
                .body(new ApiResponse<>("SUCCESS", "개인 시간표 등록 성공", null));
    }
}
