package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.TransactionSearchFilter;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatus;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import java.text.ParseException;
import java.util.Objects;

@Repository
public class BankFundTransferSpecification {
    public static Specification<BankFndTransferEntity> all(TransactionSearchFilter filter) throws ParseException {

        Specification<BankFndTransferEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal(BankFndTransferEntity_.IS_DELETED, false));
        specification = specification.and(SpecificationBuilder.notEqual(BankFndTransferEntity_.TRANSACTION_STATUS, TransactionStatus.Submitted));

        if (!Objects.isNull(filter.getRoutingType())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(BankFndTransferEntity_.ROUTING_TYPE, filter.getRoutingType()));
        }

        if (StringUtils.isNotBlank(filter.getBank())) {
            if(filter.getRoutingType().equals(RoutingType.Incoming)){
                specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(BankFndTransferEntity_.PAYER_BANK_ID, filter.getBank()));
            }else{
                specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(BankFndTransferEntity_.BEN_BANK_ID, filter.getBank()));
            }
       }

        if (!Objects.isNull(filter.getTransactionStatus())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(BankFndTransferEntity_.TRANSACTION_STATUS, filter.getTransactionStatus()));
        }

        if (StringUtils.isNotBlank(filter.getVoucher())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BankFndTransferEntity_.VOUCHER_NUMBER, filter.getVoucher()));
        }

        if (StringUtils.isNotBlank(filter.getReference())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BankFndTransferEntity_.REFERENCE_NUMBER, filter.getReference()));
        }

        if (StringUtils.isNotBlank(filter.getBatchNumber())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BankFndTransferEntity_.PARENT_BATCH_NUMBER, filter.getBatchNumber()));
        }

        if (StringUtils.isNotBlank(filter.getCurrency())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(BankFndTransferEntity_.CURRENCY_ID, filter.getCurrency()));
        }

        if (!Objects.isNull(filter.getFromDate())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.dateGreaterThanEqual(BankFndTransferEntity_.CREATED_AT, filter.getFromDate()));
        }

//        if (!Objects.isNull(filter.getToDate())) {
//            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.dateLessThanEqual(BankFndTransferEntity_.CREATED_AT, filter.getToDate()));
//        }
        if (!Objects.isNull(filter.getToDate())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.dateEqual(BankFndTransferEntity_.CREATED_AT, filter.getToDate()));
        }
        if (StringUtils.isNotBlank(filter.getDept())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(BankFndTransferEntity_.DEPARTMENT_ID, filter.getDept()));
        }

        return specification;
    }
}