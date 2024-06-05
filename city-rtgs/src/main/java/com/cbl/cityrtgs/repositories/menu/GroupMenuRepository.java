package com.cbl.cityrtgs.repositories.menu;

import com.cbl.cityrtgs.models.entitymodels.menu.GroupMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GroupMenuRepository extends JpaRepository<GroupMenu, Long> {

    Optional<GroupMenu> findByGroupId(Long id);

    @Query(value = "SELECT * FROM TBL_GROUPS_MENUS WHERE GROUP_ID = " +
            "(SELECT GROUP_ID FROM TBL_GROUPS_ACCESS WHERE ID = " +
            "(SELECT GROUP_ACCESS_ID FROM TBL_USERINFO WHERE ID = :userId))", nativeQuery = true)
    Optional<GroupMenu> findGroupMenuByUserId(Long userId);
}
