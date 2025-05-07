package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {}