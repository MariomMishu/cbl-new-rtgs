package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class SISpecification {

    public static Specification<SiUpcomingItem> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<SiUpcomingItem> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), status.equalsIgnoreCase("ACTIVE"));
    }
    public static Specification<SiUpcomingItem> hasState(String state) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("executionState"), state);
    }

    public static Specification<SiUpcomingItem> hasExpiryDate(String expiryDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("expiryDate"), LocalDate.parse(expiryDate));
    }

    public static Specification<SiUpcomingItem> hasBeneficiaryAccount(String beneficiaryAccountNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("beneficiaryAccountNo"), beneficiaryAccountNo);
    }

    public static Specification<SiUpcomingItem> hasPayerAccount(String payerAccountNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("payerAccountNo"), payerAccountNo);
    }

    public static Specification<SiUpcomingItem> hasCurrency(String currency) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("siConfiguration").get("currency").get("shortCode"),
                currency);
    }
}
