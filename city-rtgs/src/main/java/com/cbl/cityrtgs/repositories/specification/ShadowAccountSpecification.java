package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity_;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity_;
import com.cbl.cityrtgs.models.entitymodels.configuration.ShadowAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ShadowAccountEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class ShadowAccountSpecification {

    public static Specification<ShadowAccountEntity> all(ShadowAccountFilter filter) {

        Specification<ShadowAccountEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal(ShadowAccountEntity_.IS_DELETED, false));

        if (StringUtils.isNotBlank(filter.getRtgsSettlementAccount())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(ShadowAccountEntity_.RTGS_SETTLEMENT_ACCOUNT, filter.getRtgsSettlementAccount()));
        }
        if (StringUtils.isNotBlank(filter.getIncomingGl())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(ShadowAccountEntity_.INCOMING_GL, filter.getIncomingGl()));
        }
        if (StringUtils.isNotBlank(filter.getOutgoingGl())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(ShadowAccountEntity_.OUTGOING_GL, filter.getOutgoingGl()));
        }

        if (StringUtils.isNotBlank(filter.getBank())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(ShadowAccountEntity_.BANK, BankEntity_.NAME, filter.getBank()));
        }

        if (StringUtils.isNotBlank(filter.getCurrency())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(ShadowAccountEntity_.CURRENCY, CurrencyEntity_.SHORT_CODE, filter.getCurrency()));
        }

        return specification;
    }
}