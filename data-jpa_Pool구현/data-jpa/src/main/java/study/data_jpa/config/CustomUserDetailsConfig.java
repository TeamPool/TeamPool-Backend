package study.data_jpa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.data_jpa.entity.User;
import study.data_jpa.repository.UserRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsConfig implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetails loadUserByUsername(String studentNumber) throws UsernameNotFoundException {
        User user = userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        return new org.springframework.security.core.userdetails.User(
                user.getStudentNumber(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
//merge

