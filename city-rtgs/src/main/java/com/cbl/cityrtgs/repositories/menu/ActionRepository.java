package com.cbl.cityrtgs.repositories.menu;


import com.cbl.cityrtgs.models.entitymodels.menu.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

    @Query("SELECT A FROM Action A WHERE A.id != :id AND A.name = :name OR A.action = :action")
    Optional<Action> findDuplicate(Long id, String name, String action);

    Page<Action> findAllByIsDeletedFalse(Pageable pageable);

    Boolean existsByNameOrAction(String name, String action);

    Optional<Action> findByIdAndIsDeletedFalse(Long id);

    List<Action> findAllByIsDeletedFalse();
}
