package com.cbl.cityrtgs.repositories.menu;

import com.cbl.cityrtgs.models.entitymodels.menu.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Page<Group> findAllByIsDeletedFalse(Pageable pageable);

    @Query("SELECT G FROM Group G WHERE G.id != :id AND G.name = :name AND G.isDeleted = FALSE")
    Optional<Group> findDuplicate(Long id, String name);

    Boolean existsByName(String name);

    Optional<Group> findByIdAndIsDeletedFalse(Long id);

    List<Group> findAllByIsDeletedFalse();
}
