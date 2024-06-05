package com.cbl.cityrtgs.models.entitymodels.user;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.userInfo.UserStatus;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = EntityConstant.USERINFOAUD)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserAudEntity {

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    public String createdBy;
    @Column(name = "UPDATED_BY")
    public String updatedBy;
    @Column(name = "ISDELETED", nullable = false)
    public boolean isDeleted = false;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "REV", length = 10)
    private Long rev;

//    @Column(name = "CR_LIMIT")
//    private BigDecimal crLimit;
//
//    @Column(name = "DR_LIMIT")
//    private BigDecimal drLimit;
    @Column(name = "REVTYPE", length = 3)
    private int revType;
    @ManyToOne(fetch = FetchType.LAZY)
    private BranchEntity branch;
    @Column(name = "CELL_NO")
    private String cell_No;
    @Column(name = "EMAIL_ADDR")
    private String emailAddr;
    @Column(name = "EMPLOYEE_ID")
    private String employeeId;
    @Column(name = "ENCPASSWORD")
    private String encPassword;
    @Column(name = "FULLNAME")
    private String fullName;
    @Column(name = "MD5PASSWORD")
    private String md5Password;

//    @Column(name = "TXN_VRF_LIMIT")
//    private BigDecimal txnVrfLimit;
    @Column(name = "PASSWORD_EXPIRE_DATE")
    private Date passwordExpireDate;
    @Column(name = "PHONE_NO")
    private String phone_No;
    @Column(name = "REC_STATUS")
    @Enumerated(EnumType.STRING)
    private UserStatus recStatus;
    @Column(name = "USERNAME")
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    private DepartmentEntity dept;
    @Column(name = "ACTIVATED")
    private boolean activated = false;
    @Column(name = "BOOTHID")
    private String boothId;
    @Column(name = "BOOTH")
    private Long booth;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON")
    @CreatedDate
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_ON")
    @LastModifiedDate
    private Date updatedAt;
    @Column(name = "USER_ID")
    private Long userId;

}
