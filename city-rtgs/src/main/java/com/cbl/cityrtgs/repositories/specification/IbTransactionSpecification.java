package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.transaction.c2c.IbTransactionFilter;
import com.cbl.cityrtgs.models.entitymodels.transaction.IbTransactionEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.IbTransactionEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Repository
public class IbTransactionSpecification {

    public static Specification<IbTransactionEntity> all(IbTransactionFilter filter) {

        Specification<IbTransactionEntity> specification = SpecificationBuilder.conjunction();

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));

        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        specification = specification.and(SpecificationBuilder.dateEqual(IbTransactionEntity_.CREATED_AT, currentDate));

        if (StringUtils.isNotBlank(filter.getRequestReference())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("requestReference", filter.getRequestReference()));
        }

        if (StringUtils.isNotBlank(filter.getResponseReference())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("responseReference", filter.getResponseReference()));
        }

        if (StringUtils.isNotBlank(filter.getNarration())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("narration", filter.getNarration()));
        }

        if (StringUtils.isNotBlank(filter.getCurrency())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("currency", filter.getCurrency()));
        }
        if (StringUtils.isNotBlank(filter.getBenBranchRoutingNo())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("benBranchRoutingNo", filter.getBenBranchRoutingNo()));
        }

        if (StringUtils.isNotBlank(filter.getBenName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("benName", filter.getBenName()));
        }

        if (StringUtils.isNotBlank(filter.getBenAccount())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("benAccount", filter.getBenAccount()));
        }

        if (StringUtils.isNotBlank(filter.getPayerAccount())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("payerAccount", filter.getPayerAccount()));
        }

        if (StringUtils.isNotBlank(filter.getPayerName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("payerName", filter.getPayerName()));
        }

        if (StringUtils.isNotBlank(filter.getTransactionStatus())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(IbTransactionEntity_.TRANSACTION_STATUS, filter.getTransactionStatus()));
        }
        if (StringUtils.isNotBlank(filter.getDeliveryChannel())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(IbTransactionEntity_.DELIVERY_CHANNEL, filter.getDeliveryChannel()));
        }

        if (!Objects.isNull(filter.getTransactionDate())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.dateEqual(IbTransactionEntity_.TRANSACTION_DATE, filter.getTransactionDate()));
        }
        if (!Objects.isNull(filter.getSettlementDate())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.dateEqual(IbTransactionEntity_.SETTLEMENT_DATE, filter.getSettlementDate()));
        }

        return specification;
    }
}