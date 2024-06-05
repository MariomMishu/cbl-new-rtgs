package com.cbl.cityrtgs.models.dto.si;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CustomerFndTransferTxn;
import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
@ToString
public class CustomerFundTransferTransaction {

    private Long id;
    private Long customerFundTransferId;
    private String benName;
    private String benAccNo;
    private String benAccType;
    private Long benBankId;
    private Long benBranchId;
    private String benBankBic;
    private String benBranchRoutingNo;
    private boolean batchTxn;
    private String binCode;
    private String commissionerateEconomicCode;
    private String payerAccNo;
    private String payerName;
    private String payerInsNo;
    private String payerAccType;
    private Long payerBranchId;
    private Long payerBankId;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal charge;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal vat;
    private String narration;
    private String lcNumber;
    private String rmtCustOfficeCode;
    private int rmtRegYear;
    private String rmtRegNum;
    private String rmtDeclareCode;
    private String rmtCusCellNo;

    public static CustomerFndTransferEntity toEntityModel(CustomerFundTransferTransaction domain) {

        return CustomerFndTransferEntity.builder()
                .narration(domain.getNarration())
                .routingType(RoutingType.Outgoing)
                .payerAccNo(domain.getPayerAccNo())
                .payerName(domain.getPayerName())
                .payerInsNo(domain.getPayerInsNo())
                .benBankId(domain.getBenBankId())
                .benBranchId(domain.getBenBranchId())
                .benAccNo(domain.getBenAccNo())
                .benName(domain.getBenName())
                .fcOrgAccountType(domain.getBenAccType())
                .fcRecAccountType(domain.getPayerAccType())
                .lcNumber(domain.getLcNumber())
                .rmtCusCellNo(domain.getRmtCusCellNo())
                .rmtCustOfficeCode(domain.getRmtCustOfficeCode())
                .rmtDeclareCode(domain.getRmtDeclareCode())
                .rmtRegNum(domain.getRmtRegNum())
                .rmtRegYear(domain.getRmtRegYear())
                .amount(domain.getAmount() != null ? domain.getAmount() : BigDecimal.ZERO)
                .build();

    }

    public static CustomerFndTransferEntity updateEntityModel(CustomerFundTransferTransaction domain, CustomerFndTransferEntity entity) {

        entity.setAmount(domain.getAmount());
        entity.setNarration(domain.getNarration());
        entity.setRoutingType(RoutingType.Outgoing);
        entity.setPayerAccNo(domain.getPayerAccNo());
        entity.setPayerName(domain.getPayerName());
        entity.setPayerInsNo(domain.getPayerInsNo());
        entity.setBenBankId(domain.getBenBankId());
        entity.setBenBranchId(domain.getBenBranchId());
        entity.setBenAccNo(domain.getBenAccNo());
        entity.setBenName(domain.getBenName());
        entity.setFcOrgAccountType(domain.getBenAccType());
        entity.setFcRecAccountType(domain.getPayerAccType());
        entity.setLcNumber(domain.getLcNumber());
        entity.setRmtCusCellNo(domain.getRmtCusCellNo());
        entity.setRmtCustOfficeCode(domain.getRmtCustOfficeCode());
        entity.setRmtDeclareCode(domain.getRmtDeclareCode());
        entity.setRmtRegNum(domain.getRmtRegNum());
        entity.setRmtRegYear(domain.getRmtRegYear());

        return entity;
    }

    public static CustomerFundTransferTransaction toModel(SiUpcomingItem request){

        return CustomerFundTransferTransaction.builder()
                .payerName(request.getPayerName())
                .payerAccNo(request.getPayerAccountNo())
                .payerAccType(request.getPayerAccountType())
                .benName(request.getBeneficiaryName())
                .benAccType(request.getBeneficiaryAccountNo())
                .benAccNo(request.getBeneficiaryAccountNo())
                .benBankId(request.getBeneficiaryBankId())
                .benBranchId(request.getBeneficiaryBranchId())
                .benBranchRoutingNo(request.getBeneficiaryBranchRoutingNo())
                .benBankBic(request.getBeneficiaryBankBic())
                .narration(request.getNarration())
                .amount(request.getAmount())
                .lcNumber(request.getLcNo())
                .build();
    }
    public static CustomerFndTransferTxn toTXnModel(SiUpcomingItem request){

        return CustomerFndTransferTxn.builder()
                .payerName(request.getPayerName())
                .payerAccNo(request.getPayerAccountNo())
                .payerAccType(request.getPayerAccountType())
                .benName(request.getBeneficiaryName())
                .benAccType(request.getBeneficiaryAccountType())
                .benAccNo(request.getBeneficiaryAccountNo())
                .benBankId(request.getBeneficiaryBankId())
                .benBranchId(request.getBeneficiaryBranchId())
                .benBranchRoutingNo(request.getBeneficiaryBranchRoutingNo())
                .benBankBic(request.getBeneficiaryBankBic())
                .narration(request.getNarration())
                .amount(request.getAmount())
                .lcNumber(request.getLcNo())
                .sourceType(1L)
                .sourceReference(request.getId())
                .build();
    }

    public static CustomerFundTransferTransaction toCustomerFundTransferTransactionModel(SiRequest request){

        return CustomerFundTransferTransaction.builder()
                .payerName(request.getPayerName())
                .payerAccNo(request.getPayerAccountNo())
                .payerAccType(request.getPayerAccountType())
                .benName(request.getBeneficiaryName())
                .benAccType(request.getBeneficiaryAccountNo())
                .benAccNo(request.getBeneficiaryAccountNo())
                .benBankId(request.getBeneficiaryBankId())
                .benBranchId(request.getBeneficiaryBranchId())
                .benBranchRoutingNo(request.getBeneficiaryBranchRoutingNo())
                .benBankBic(request.getBeneficiaryBankBic())
                .narration(request.getNarration())
                .amount(request.getAmount() != null ? request.getAmount() : BigDecimal.ZERO)
                .lcNumber(request.getLcNo())
                .build();
    }

    public static CustomerFundTransferTransaction toCustomerFundTransferTransactionModelEdit(SiEditRequest request){

        return CustomerFundTransferTransaction.builder()
                .id(request.getId())
                .customerFundTransferId(request.getCustomerFundTransferId())
                .payerName(request.getPayerName())
                .payerAccNo(request.getPayerAccountNo())
                .payerAccType(request.getPayerAccountType())
                .benName(request.getBeneficiaryName())
                .benAccType(request.getBeneficiaryAccountNo())
                .benAccNo(request.getBeneficiaryAccountNo())
                .benBankId(request.getBeneficiaryBankId())
                .benBranchId(request.getBeneficiaryBranchId())
                .benBranchRoutingNo(request.getBeneficiaryBranchRoutingNo())
                .benBankBic(request.getBeneficiaryBankBic())
                .narration(request.getNarration())
                .amount(request.getAmount())
                .lcNumber(request.getLcNo())
                .build();
    }
}
