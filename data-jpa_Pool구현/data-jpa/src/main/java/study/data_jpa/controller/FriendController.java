package study.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.*;
import study.data_jpa.entity.User;
import study.data_jpa.repository.UserRepository;
import study.data_jpa.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> addFriend(@AuthenticationPrincipal UserDetails userDetails,
                                                         @RequestBody FriendRequestDto requestDto) {
        String studentNumber = userDetails.getUsername();
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("유저 없음"));
        friendService.addFriend(user.getId(), requestDto);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "친구 추가 성공", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FriendResponseDto>>> getFriends(@AuthenticationPrincipal UserDetails userDetails) {
        String studentNumber = userDetails.getUsername();
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        List<FriendResponseDto> friends = friendService.getFriends(user.getId());
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "친구 목록 조회 성공", friends));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<ApiResponse<Object>> deleteFriend(@AuthenticationPrincipal UserDetails userDetails,
                                                            @PathVariable Long friendId) {
        String studentNumber = userDetails.getUsername();
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        friendService.deleteFriend(user.getId(), friendId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "친구 삭제 성공", null));
    }
}
