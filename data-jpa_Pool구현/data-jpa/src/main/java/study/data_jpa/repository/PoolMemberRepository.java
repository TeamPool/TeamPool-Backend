package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.entity.Pool;
import study.data_jpa.entity.PoolMember;
import study.data_jpa.entity.User;

import java.util.List;

public interface PoolMemberRepository extends JpaRepository<PoolMember, Long> {
    @Query("SELECT pm.user FROM PoolMember pm WHERE pm.pool.id = :poolId")
    List<User> findUsersByPoolId(@Param("poolId") Long poolId);

    @Query("SELECT pm.pool FROM PoolMember pm WHERE pm.user.id = :userId")
    List<Pool> findPoolsByUserId(@Param("userId") Long userId);
}
