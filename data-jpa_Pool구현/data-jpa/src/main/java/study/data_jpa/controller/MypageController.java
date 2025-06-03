package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.*;
import study.data_jpa.service.MypageService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService MypageService;

    // 닉네임 변경 (POST로 수정됨)
    @PostMapping("/nickname")
    public ResponseEntity<ApiResponse<Object>> updateNickname(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestBody NicknameUpdateRequestDto requestDto) {
        String studentNumber = userDetails.getUsername();
        MypageService.updateNickname(studentNumber, requestDto);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "닉네임 변경 완료", null));
    }

    // 닉네임 조회
    @GetMapping("/nickname")
    public ResponseEntity<ApiResponse<NicknameResponseDto>> getNickname(@AuthenticationPrincipal UserDetails userDetails) {
        String studentNumber = userDetails.getUsername();
        String nickname = MypageService.getNickname(studentNumber);

        NicknameResponseDto responseDto = new NicknameResponseDto(studentNumber, nickname);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "유저 검색 성공", responseDto));
    }


    // 계정 삭제
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Object>> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        String studentNumber = userDetails.getUsername();
        MypageService.deleteUserByStudentNumber(studentNumber);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "계정 삭제 성공", null));
    }
}
