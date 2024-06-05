package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeSetupEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeSetupEntity_;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class ChargeSetupSpecification {

    public static Specification<ChargeSetupEntity> all(ChargeSetupFilter filter) {

        Specification<ChargeSetupEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal(ChargeSetupEntity_.IS_DELETED, false));
        specification = specification.and(SpecificationBuilder.equal(ChargeSetupEntity_.STATUS, true));

        if (Objects.nonNull(filter.getFromAmount())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(ChargeSetupEntity_.FROM_AMOUNT, filter.getFromAmount()));
        }
        if (Objects.nonNull(filter.getVatAmount())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(ChargeSetupEntity_.TO_AMOUNT, filter.getToAmount()));
        }
        if (Objects.nonNull(filter.getChargeAmount())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(ChargeSetupEntity_.CHARGE_AMOUNT, filter.getChargeAmount()));
        }
        if (Objects.nonNull(filter.getVatAmount())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(ChargeSetupEntity_.VAT_AMOUNT, filter.getVatAmount()));
        }
        if (Objects.nonNull(filter.getVatGl())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(ChargeSetupEntity_.VAT_GL, filter.getVatGl()));
        }
        if (Objects.nonNull(filter.getChargeGl())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(ChargeSetupEntity_.CHARGE_GL, filter.getChargeGl()));
        }
        if (Objects.nonNull(filter.getCurrency())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(ChargeSetupEntity_.CURRENCY, CurrencyEntity_.SHORT_CODE, filter.getCurrency()));
        }
        return specification;
    }
}