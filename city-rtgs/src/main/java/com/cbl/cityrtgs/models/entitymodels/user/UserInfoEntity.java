package com.cbl.cityrtgs.models.entitymodels.user;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.userInfo.UserStatus;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.RoleEntity;
import com.cbl.cityrtgs.models.entitymodels.menu.GroupAccess;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = EntityConstant.USERINFO)
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CELL_NO")
    private String cellNo;

    @Column(name = "EMAIL_ADDR", unique = true, nullable = false)
    private String emailAddr;

    @Column(name = "EMPLOYEE_ID", unique = true, nullable = false)
    private String employeeId;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "PASSWORD_EXPIRE_DATE")
    private Date passwordExpireDate;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "REC_STATUS")
    @Enumerated(EnumType.STRING)
    private UserStatus recStatus;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "USER_LEVEL")
    private int userLevel;

    @Column(name = "ACTIVATED")
    private boolean activated = false;

    @Column(name = "BOOTHID")
    private String boothId;

    @Column(name = "BOOTH")
    private Long booth;

    @Column(name = "PASSWORD")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON")
    @CreatedDate
    private Date createdAt;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    public String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_ON")
    @LastModifiedDate
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    public String updatedBy;

    @Column(name = "ISDELETED", nullable = false)
    public boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BranchEntity branch;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private DepartmentEntity dept;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ProfileEntity profile;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "TBL_USERROLE",
            joinColumns = @JoinColumn(name = "USERID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLEID", referencedColumnName = "ID")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @ManyToOne
    private GroupAccess groupAccess;

    public UserInfoEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
