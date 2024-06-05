package com.cbl.cityrtgs.models.entitymodels.menu;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.*;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity
@Table(name = EntityConstant.GROUPS_MENUS)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMenu {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "GROUP_ID", unique = true)
    private Group group;

    @Column(name = "MENU")
    @Lob
    private String menu;
}
