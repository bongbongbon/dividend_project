package com.sample.sample.persist;

import com.sample.sample.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

   List<DividendEntity> findAllByCompanyId(Long companyId);

   boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);
}
