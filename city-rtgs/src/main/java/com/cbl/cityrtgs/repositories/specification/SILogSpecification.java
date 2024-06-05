package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.entitymodels.si.SiHistory;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class SILogSpecification {

    public static Specification<SiHistory> hasDepartment(Long departmentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy").get("dept").get("id"), departmentId);
    }

    public static Specification<SiHistory> hasDescription(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("payerName"), description);
    }

    public static Specification<SiHistory> hasError(String error) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("message"), error);
    }
    public static Specification<SiHistory> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<SiHistory> hasFrequency(Long frequency) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("frequency"), frequency);
    }

    public static Specification<SiHistory> hasDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dueDate"), date);
    }
}
