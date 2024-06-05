package com.cbl.cityrtgs.repositories.si;

import com.cbl.cityrtgs.models.entitymodels.si.SiHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
//@Transactional
public interface SiHistoryRepository extends JpaRepository<SiHistory, Long>, PagingAndSortingRepository<SiHistory, Long> {

    @Query("SELECT SH FROM SiHistory SH ORDER BY SH.id DESC")
    List<SiHistory> findAll();

    Page<SiHistory> findAll(Specification<SiHistory> specification, Pageable pageable);
}
