package com.cbl.cityrtgs.models.entitymodels.transaction;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = EntityConstant.ACCOUNTTRANSACTIONREGISTER)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionRegisterEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ACCOUNTNO")
    private String accountNo;

    @Column(name = "COUNTERACCOUNTNO")
    private String counterAccountNo;

    @Column(name = "CREDITAMOUNTCCY")
    private BigDecimal creditAmountCcy;

    @Column(name = "CREDITAMOUNTLCY")
    private BigDecimal creditAmountLcy;

    @Column(name = "DEBITAMOUNTCCY")
    private BigDecimal debitAmountCcy;

    @Column(name = "DEBITAMOUNTLCY")
    private BigDecimal debitAmountLcy;

    @Column(name = "POSTBALANCE")
    private BigDecimal postBalance;

    @Column(name = "SETTLEMENTDDATE")
    private Date settlementDate;

    @Column(name = "TRANSACTIONDATE")
    private Date transactionDate;

    @Column(name = "TRANSACTIONREFERENCENUMBER")
    private String transactionReferenceNumber;

    @Column(name = "IS_VALID")
    private boolean isValid;

    @Column(name = "VOUCHERNUMBER")
    private String voucherNumber;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "CREDITOR_ID")
    private Long creditorId;

    @Column(name = "DEBITOR_ID")
    private Long debitorId;

    @Column(name = "NARRATION")
    private String narration;

    @Column(name = "DEBITORBRANCH_ID")
    private Long debitorBranchId;

    @Column(name = "CREDITORBRANCH_ID")
    private Long creditorBranchId;

    @Column(name = "CHARGE")
    private BigDecimal charge;

    @Column(name = "VAT")
    private BigDecimal vat;

    @Column(name = "ACCOUNTTYPEID")
    private Long accountTypeId;

    @Column(name = "DEBITCREDITSUM")
    private BigDecimal debitCreditSum;

    @Column(name = "CHARGEGL")
    private String chargeGl;

    @Column(name = "VATGL")
    private String vatGl;

    @Column(name = "ROUTINGTYPE")
    @Enumerated(EnumType.STRING)
    private RoutingType routingType;

}
