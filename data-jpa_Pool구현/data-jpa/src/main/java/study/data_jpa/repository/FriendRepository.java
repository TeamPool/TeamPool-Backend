package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entity.Friend;
import study.data_jpa.entity.User;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserId(Long userId);
    void deleteByUserAndFriendUser(User user, User friendUser);

    boolean existsByUser_IdAndFriendUser_Id(Long userId, Long friendUserId);
}