package com.cbl.cityrtgs.repositories.menu;

import com.cbl.cityrtgs.models.entitymodels.menu.GroupAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GroupAccessRepository extends JpaRepository<GroupAccess, Long> {

    Optional<GroupAccess> findByGroupId(Long groupId);

    @Query(value = "(SELECT * FROM TBL_GROUPS_ACCESS WHERE ID = (SELECT GROUP_ACCESS_ID FROM TBL_USERINFO WHERE ID = :userId))", nativeQuery = true)
    GroupAccess findGroupAccessByUserId(Long userId);

    Optional<GroupAccess> findUsersByGroupId(Long groupId);
}
