package com.cbl.cityrtgs.models.entitymodels.menu;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import lombok.*;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = EntityConstant.GROUPS_ACCESS)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupAccess {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @Transient
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ACCESS_ID")
    private Collection<UserInfoEntity> users;
}
