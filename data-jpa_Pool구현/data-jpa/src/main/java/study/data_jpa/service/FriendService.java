package study.data_jpa.service;

import study.data_jpa.dto.*;
import study.data_jpa.entity.*;
import study.data_jpa.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 친구 추가
    public void addFriend(Long userId, FriendRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        User friendUser = userRepository.findByStudentNumber(requestDto.getStudentNumber())
                .orElseThrow(() -> new RuntimeException("친구로 추가할 유저가 없음"));

        if (friendRepository.existsByUser_IdAndFriendUser_Id(user.getId(), friendUser.getId())) {
            throw new RuntimeException("이미 친구입니다.");
        }

        Friend friend1 = new Friend();
        friend1.changeUser(user);
        friend1.changeFriendUser(friendUser);

        Friend friend2 = new Friend();
        friend2.changeUser(friendUser);
        friend2.changeFriendUser(user);

        friendRepository.save(friend1);
        friendRepository.save(friend2);
    }

    // 친구 목록 조회
    public List<FriendResponseDto> getFriends(Long userId) {
        List<Friend> friends = friendRepository.findByUserId(userId);

        return friends.stream()
                .map(friend -> {
                    User friendUser = friend.getFriendUser();
                    return new FriendResponseDto(
                            friend.getId(),
                            friendUser.getNickname(),
                            friendUser.getStudentNumber()
                    );
                })
                .collect(Collectors.toList());
    }

    // 친구 삭제
    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("해당 친구 관계가 없음"));

        if (!friend.getUser().getId().equals(userId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        User me = friend.getUser();
        User target = friend.getFriendUser();

        friendRepository.delete(friend);
        friendRepository.deleteByUserAndFriendUser(target, me);
    }
}
