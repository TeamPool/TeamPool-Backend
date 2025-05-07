package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entity.PoolMember;

public interface PoolMemberRepository extends JpaRepository<PoolMember, Long> {}
