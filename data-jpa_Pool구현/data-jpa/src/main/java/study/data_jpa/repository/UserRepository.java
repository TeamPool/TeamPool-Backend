package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByStudentNumber(String studentNumber); // 학번으로 사용자 찾기
    boolean existsByStudentNumber(String studentNumber); // 학번 중복 확인
    boolean existsByNickname(String nickname);  // 닉네임 중복 확인
}

