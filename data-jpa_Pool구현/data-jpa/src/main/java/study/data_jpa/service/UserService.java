package study.data_jpa.service;

import study.data_jpa.entity.User;
import study.data_jpa.repository.UserRepository;
import study.data_jpa.jwt.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String registerUser(String studentNumber, String nickname, String password) {
        validateDuplicateUser(studentNumber, nickname);

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setStudentNumber(studentNumber);
        user.setNickname(nickname);
        user.setPassword(encodedPassword);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return "회원가입 성공!";
    }

    public void validateDuplicateUser(String studentNumber, String nickname) {
        if (userRepository.existsByStudentNumber(studentNumber)) {
            throw new IllegalStateException("이미 존재하는 학번입니다.");
        }
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    public void checkDuplicate(String studentNumber, String nickname) {
        if (userRepository.existsByStudentNumber(studentNumber) ||
                userRepository.existsByNickname(nickname)) {
            throw new IllegalStateException("이미 사용 중입니다.");
        }
    }

    public Map<String, String> loginUser(String studentNumber, String password) {
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 학번입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createToken(user.getStudentNumber(), "ROLE_USER");
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getStudentNumber());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    public String refreshAccessToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalStateException("유효하지 않은 리프레시 토큰입니다.");
        }

        String studentNumber = jwtTokenProvider.getUsername(refreshToken);

        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new IllegalStateException("사용자 정보를 찾을 수 없습니다."));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new IllegalStateException("리프레시 토큰이 일치하지 않습니다.");
        }

        return jwtTokenProvider.createToken(studentNumber, "ROLE_USER");
    }
}
