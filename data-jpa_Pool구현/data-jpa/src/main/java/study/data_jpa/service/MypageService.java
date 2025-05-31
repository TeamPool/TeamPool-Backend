package study.data_jpa.service;

import study.data_jpa.dto.*;
import study.data_jpa.entity.*;
import study.data_jpa.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;

    public void updateNickname(String studentNumber, NicknameUpdateRequestDto requestDto) {
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        user.setNickname(requestDto.getNickname());
        userRepository.save(user);
    }

    public void deleteUserByStudentNumber(String studentNumber) {
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        userRepository.delete(user);
    }
}
