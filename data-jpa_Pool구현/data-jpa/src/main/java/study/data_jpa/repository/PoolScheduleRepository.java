package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.entity.PoolSchedule;

import java.time.LocalDate;
import java.util.List;

public interface PoolScheduleRepository extends JpaRepository<PoolSchedule, Long> {

    List<PoolSchedule> findByPool_Id(Long poolId);

    @Query("SELECT s FROM PoolSchedule s WHERE CAST(s.startDatetime AS date) = :date")
    List<PoolSchedule> findByDate(@Param("date") LocalDate date);
}
