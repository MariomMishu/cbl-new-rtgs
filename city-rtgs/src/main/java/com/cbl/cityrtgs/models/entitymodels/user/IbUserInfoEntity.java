package com.cbl.cityrtgs.models.entitymodels.user;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.DeliveryChannelEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = EntityConstant.IBUSERINFO)
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IbUserInfoEntity{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ENCPASSWORD")
    private String encPassword;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String emailAddress;

    @Column(name = "ACTIVATED")
    private boolean activated = false;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DeliveryChannelEntity deliveryChannel;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DepartmentEntity dept;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProfileEntity profile;

}
