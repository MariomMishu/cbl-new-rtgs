package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.dto.configuration.userInfo.UserStatus;
import com.cbl.cityrtgs.models.dto.projection.UserRoleProjection;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long>,
        JpaSpecificationExecutor<UserInfoEntity>,
        PagingAndSortingRepository<UserInfoEntity, Long> {

    Optional<UserInfoEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmailAddr(String email);

    @Query(value = "SELECT COUNT(*) FROM TBL_USERINFO WHERE EMAIL_ADDR = :email AND ID != :userId", nativeQuery = true)
    long checkDuplicateEmailAddr(String email, Long userId);

    @Query(value = "SELECT COUNT(*) FROM TBL_USERINFO WHERE CELL_NO = :cellNo AND ID != :userId", nativeQuery = true)
    long checkDuplicateCellNo(String cellNo, Long userId);

    Boolean existsByCellNo(String cellNo);

    Page<UserInfoEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<UserInfoEntity> findByIdAndIsDeletedFalse(Long id);

    Page<UserInfoEntity> findAllByIdInAndIsDeletedFalseAndCreatedByNotLike(Pageable pageable, List<Long> ids, String createdBy );

    Optional<UserInfoEntity> findByUsernameAndIsDeletedFalse(String userName);

   /* @Query(value = "SELECT * FROM TBL_USERINFO WHERE EMPLOYEE_ID = :employeeId AND CELL_NO = :cellNo AND ISDELETED = 0", nativeQuery = true)
    Optional<UserInfoEntity> findByEmployeeIdAndCellNo(String employeeId, String cellNo);*/

    UserInfoEntity findByEmployeeIdAndCellNo(String employeeId,String cellNo);

    Optional<UserInfoEntity> findByPhoneNo(String phoneNo);

    @Modifying
    @Query("UPDATE UserInfoEntity U SET U.activated = TRUE, U.recStatus = :userStatus WHERE U.id = :userId")
    void activateUser(Long userId, UserStatus userStatus);

    @Modifying
    @Query("UPDATE UserInfoEntity U SET U.activated = FALSE, U.recStatus = :userStatus WHERE U.id = :userId")
    void deactivateUser(Long userId, UserStatus userStatus);

    @Modifying
    @Query("UPDATE UserInfoEntity U SET U.recStatus = :userStatus WHERE U.id = :userId")
    void deleteUser(Long userId, UserStatus userStatus);

    @Query("SELECT U FROM UserInfoEntity U WHERE U.activated = false AND U.recStatus = 'InActive'")
    Page<UserInfoEntity> findAllUnapprovedUsers(Pageable pageable);

    @Query("SELECT U FROM UserInfoEntity U WHERE U.recStatus = 'Rejected'")
    Page<UserInfoEntity> findAllRejectedUsers(Pageable pageable);

    @Query(value = "SELECT COUNT(*) flag FROM (SELECT NAME FROM TBL_ROLE WHERE ID IN " +
            "(SELECT ROLEID FROM TBL_USERROLE WHERE USERID = :userId)) T " +
            "WHERE 'Admin' IN T.NAME", nativeQuery = true)
    long isUserAdmin(Long userId);

    @Modifying
    @Query(value = "UPDATE TBL_USERINFO SET GROUP_ACCESS_ID = NULL WHERE GROUP_ACCESS_ID = :groupId", nativeQuery = true)
    void deleteUserGroupAccess(long groupId);

    @Query(value = "SELECT P.USERID USER_ID,Q.ID ROLE_ID, Q.NAME ROLE " +
            "FROM TBL_USERROLE P INNER JOIN TBL_ROLE Q ON P.ROLEID = P.ROLEID " +
            "WHERE Q.NAME = 'Admin' AND P.USERID = :userId", nativeQuery = true)
    List<UserRoleProjection> getUserRole(long userId);
}
