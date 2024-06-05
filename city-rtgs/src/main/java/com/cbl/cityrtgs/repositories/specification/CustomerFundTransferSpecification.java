package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.TransactionSearchFilter;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity_;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.Objects;

@Repository
public class CustomerFundTransferSpecification {
    public static Specification<CustomerFndTransferEntity> all(TransactionSearchFilter filter) throws ParseException {

        Specification<CustomerFndTransferEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal(CustomerFndTransferEntity_.IS_DELETED, false));
        specification = specification.and(SpecificationBuilder.notEqual(BankFndTransferEntity_.TRANSACTION_STATUS.toString(), "Submitted"));

        if (!Objects.isNull(filter.getRoutingType())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(CustomerFndTransferEntity_.ROUTING_TYPE, filter.getRoutingType()));
        }

        if (StringUtils.isNotBlank(filter.getBank())) {
            if(filter.getRoutingType().equals(RoutingType.Incoming)){
                specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(CustomerFndTransferEntity_.PAYER_BANK_ID, filter.getBank()));
            }else{
                specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(CustomerFndTransferEntity_.BEN_BANK_ID, filter.getBank()));
            }
       }

        if (!Objects.isNull(filter.getTransactionStatus())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(CustomerFndTransferEntity_.TRANSACTION_STATUS, filter.getTransactionStatus()));
        }

        if (StringUtils.isNotBlank(filter.getVoucher())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(CustomerFndTransferEntity_.VOUCHER_NUMBER, filter.getVoucher()));
        }

        if (StringUtils.isNotBlank(filter.getReference())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(CustomerFndTransferEntity_.REFERENCE_NUMBER, filter.getReference()));
        }

        if (StringUtils.isNotBlank(filter.getBatchNumber())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(CustomerFndTransferEntity_.PARENT_BATCH_NUMBER, filter.getBatchNumber()));
        }

        if (StringUtils.isNotBlank(filter.getCurrency())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(CustomerFndTransferEntity_.CURRENCY_ID, filter.getCurrency()));
        }

        if (!Objects.isNull(filter.getFromDate())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.dateGreaterThanEqual(CustomerFndTransferEntity_.CREATED_AT, filter.getFromDate()));
        }

//        if (!Objects.isNull(filter.getToDate())) {
//            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.dateLessThanEqual(CustomerFndTransferEntity_.CREATED_AT, filter.getToDate()));
//        }
        if (!Objects.isNull(filter.getToDate())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.dateEqual(CustomerFndTransferEntity_.CREATED_AT, filter.getToDate()));
        }
        if (StringUtils.isNotBlank(filter.getDept())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(CustomerFndTransferEntity_.DEPARTMENT_ID, filter.getDept()));
        }

        if (StringUtils.isNotBlank(filter.getPayerAccount())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(CustomerFndTransferEntity_.PAYER_ACC_NO, filter.getPayerAccount()));
        }

        if (StringUtils.isNotBlank(filter.getBenAccount())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(CustomerFndTransferEntity_.BEN_ACC_NO, filter.getBenAccount()));
        }

        return specification;
    }
}