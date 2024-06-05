package com.cbl.cityrtgs.test;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDataEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CREATEDAT")
    private LocalDateTime createDate;

    @Column(name = "MSGID")
    private String msgid;

    @Column(name = "DATALOG")
    private String datalog;

    @Column(name = "LOGTYPE")
    private String logtype;
}
