package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupRequest;
import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeSetupEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ChargeAccountSetupMapper {
    public ChargeSetupResponse entityToDomain(ChargeSetupEntity entity) {
        ChargeSetupResponse response = new ChargeSetupResponse();
        response
                .setChargeAmount(entity.getChargeAmount().doubleValue())
                .setCurrencyId(entity.getCurrency().getId())
                .setCurrencyName(entity.getCurrency().getShortCode())
                .setChargeCurrencyId(entity.getChargeCurrency().getId())
                .setChargeCurrencyName(entity.getChargeCurrency().getShortCode())
                .setChargeGl(entity.getChargeGl())
                .setConversionRate(entity.getConversionRate().doubleValue())
                .setFromAmount(entity.getFromAmount().doubleValue())
                .setStatus(entity.isStatus())
                .setToAmount(entity.getToAmount().doubleValue())
                .setVatAmount(entity.getVatAmount().doubleValue())
                .setVatGl(entity.getVatGl())
                .setVatPercent(entity.getVatPercent().doubleValue())
                .setId(entity.getId());
        return response;
    }

    public ChargeSetupEntity domainToEntity(ChargeSetupRequest domain) {
        ChargeSetupEntity entity = new ChargeSetupEntity();
        CurrencyEntity currency = new CurrencyEntity().setId(domain.getCurrencyId());
        CurrencyEntity chargeCurrency = new CurrencyEntity().setId(domain.getChargeCurrencyId());
        entity
                .setChargeAmount(domain.getChargeAmount())
                .setCurrency(currency)
                .setChargeCurrency(chargeCurrency)
                .setChargeGl(domain.getChargeGl())
                .setConversionRate(domain.getConversionRate())
                .setFromAmount(domain.getFromAmount())
                .setStatus(domain.getStatus())
                .setToAmount(domain.getToAmount())
                .setVatAmount(domain.getVatAmount())
                .setVatGl(domain.getVatGl())
                .setVatPercent(domain.getVatPercent());
        return entity;
    }

    public ChargeSetupResponse entityToDomainAfterCalculation(ChargeSetupEntity entity) {
        ChargeSetupResponse response = new ChargeSetupResponse();
        Double charge = BigDecimal.valueOf(entity.getChargeAmount().doubleValue()).setScale(4, RoundingMode.HALF_UP).doubleValue();
        Double vat = BigDecimal.valueOf(entity.getVatAmount().doubleValue()).setScale(4, RoundingMode.HALF_UP).doubleValue();
        Double conversionRate = BigDecimal.valueOf(entity.getConversionRate().doubleValue()).setScale(4, RoundingMode.HALF_UP).doubleValue();
        Double convertedCharge = BigDecimal.valueOf((charge / conversionRate)).setScale(4, RoundingMode.HALF_UP).doubleValue();
        Double convertedVat = BigDecimal.valueOf((vat / conversionRate)).setScale(4, RoundingMode.HALF_UP).doubleValue();

        response
                .setChargeAmount(convertedCharge)
                .setCurrencyId(entity.getCurrency().getId())
                .setCurrencyName(entity.getCurrency().getShortCode())
                .setChargeCurrencyId(entity.getChargeCurrency().getId())
                .setChargeCurrencyName(entity.getChargeCurrency().getShortCode())
                .setChargeGl(entity.getChargeGl())
                .setConversionRate(entity.getConversionRate().doubleValue())
                .setFromAmount(entity.getFromAmount().doubleValue())
                .setStatus(entity.isStatus())
                .setToAmount(entity.getToAmount().doubleValue())
                .setVatAmount(convertedVat)
                .setVatGl(entity.getVatGl())
                .setVatPercent(entity.getVatPercent().doubleValue())
                .setId(entity.getId());
        return response;
    }

}
