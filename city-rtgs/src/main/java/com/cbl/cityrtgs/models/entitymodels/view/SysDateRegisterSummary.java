package com.cbl.cityrtgs.models.entitymodels.view;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

//@Entity
//@Table(name = EntityConstant.SYSDATEREGISTERSUMMARY)
//@Getter
//@Setter
//@Accessors(chain = true)
//@NoArgsConstructor
//@AllArgsConstructor

@Entity
@Immutable
@Getter
@Table(name = EntityConstant.SYSDATEREGISTERSUMMARY)
public class SysDateRegisterSummary {
    @Id
    private Long id;
    @Column(name = "ACCOUNTNO")
    private String accountNo;
    @Column(name = "TRANSACTIONDATE")
    private Date transactionDate;
    @Column(name = "TOTALBALANCE")
    private BigDecimal totalBalance;
    @Column(name = "ACCOUNT_ID")
    private Long account;

    public SysDateRegisterSummary(String accountNo, Date transactionDate, BigDecimal totalBalance, Long account) {
        this.accountNo = accountNo;
        this.transactionDate = transactionDate;
        this.totalBalance = totalBalance;
        this.account = account;
    }

    public SysDateRegisterSummary() {

    }
}
