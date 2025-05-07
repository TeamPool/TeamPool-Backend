package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
