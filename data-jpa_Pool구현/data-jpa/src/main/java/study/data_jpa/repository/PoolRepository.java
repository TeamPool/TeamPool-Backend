package study.data_jpa.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.entity.Pool;

import java.util.List;
import java.util.Optional;

public interface PoolRepository extends JpaRepository<Pool, Long> {

    @Query("SELECT p FROM Pool p JOIN p.members m WHERE m.user.id = :userId")
    List<Pool> findPoolsByUserId(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"members"})
    @Query("SELECT p FROM Pool p WHERE p.id = :id")
    Optional<Pool> findDetailedPoolById(@Param("id") Long id);

}
