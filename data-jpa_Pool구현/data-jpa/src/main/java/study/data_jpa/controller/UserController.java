package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.*;
import study.data_jpa.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody SignUpRequest request) {
        String result = userService.registerUser(
                request.getStudentNumber(), request.getNickname(), request.getPassword());
        return ResponseEntity.ok(new ApiResponse<>("200", "회원가입 성공", result));
    }

    @GetMapping("/studentNumber-signup-dup")
    public ResponseEntity<ApiResponse<String>> checkStudentNumberDuplicate(
            @RequestParam String studentNumber
    ) {
        userService.checkStudentNumberDuplicate(studentNumber);
        return ResponseEntity.ok(new ApiResponse<>("200", "사용 가능한 학번입니다.", null));
    }

    @GetMapping("/nickname-signup-dup")
    public ResponseEntity<ApiResponse<String>> checkNicknameDuplicate(
            @RequestParam String nickname
    ) {
        userService.checkNicknameDuplicate(nickname);
        return ResponseEntity.ok(new ApiResponse<>("200", "사용 가능한 닉네임입니다.", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody LoginRequest request) {
        Map<String, String> tokens = userService.loginUser(
                request.getStudentNumber(), request.getPassword());
        return ResponseEntity.ok(new ApiResponse<>("200", "로그인 성공", tokens));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Map<String, String>>> refresh(@RequestBody RefreshRequest request) {
        String newAccessToken = userService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(new ApiResponse<>("200", "토큰 재발급 성공", Map.of("accessToken", newAccessToken)));
    }
}
//test