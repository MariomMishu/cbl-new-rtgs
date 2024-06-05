package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.services.si.utility.SiUtility;
import org.springframework.data.jpa.domain.Specification;

public class SubmittedSISpecification {

    public static Specification<SiUpcomingItem> hasDepartment(Long departmentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("siConfiguration").get("department").get("id"), departmentId);
    }

    public static Specification<SiUpcomingItem> hasNotUser(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("siConfiguration").get("createdBy").get("id"), userId);
    }

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

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("siConfiguration").get("expiryDate"), SiUtility.toDate(expiryDate));
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
