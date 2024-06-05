package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.common.enums.OrderBy;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class SpecificationBuilder {
    public static <T> Specification<T> conjunction() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static <T> Specification<T> orderBy(String key, OrderBy order) {
        if (order.getCode() == 1) {
            return (root, query, criteriaBuilder) -> (Predicate) query.orderBy(criteriaBuilder.desc(root.get(key)));
        }
        return (root, query, criteriaBuilder) -> (Predicate) query.orderBy(criteriaBuilder.asc(root.get(key)));
    }

    public static <T> Specification<T> equal(String key, Object value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(key), value);
    }

    public static <T> Specification<T> notEqual(String key, Object value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(key), value);
    }

    public static <T> Specification<T> greaterThan(String key, Number value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.gt(root.get(key), value);
    }

    public static <T> Specification<T> dateTimeGreaterThanEqual(String key, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(key), (LocalDateTime) value);
    }

    public static <T> Specification<T> dateTimeLessThanEqual(String key, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(key), (LocalDateTime) value);
    }

    public static <T> Specification<T> dateTimeEqual(String key, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(key), (Date) value);
    }

    public static <T> Specification<T> lessThan(String key, Number value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lt(root.get(key), value);
    }

    public static <T> Specification<T> notNull(String key) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(key));
    }

    public static <T> Specification<T> like(String key, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get(key)), "%" + value.toUpperCase() + "%");
    }

    public static <T> Specification<T> notLike(String key, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notLike(criteriaBuilder.upper(root.get(key)), "%" + value.toUpperCase() + "%");
    }

    public static <T> Specification<T> in(String key, List<?> values) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(key)).value(values);
    }

    public static <T> Specification<T> equal(String parentKey, String childKey, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(parentKey).get(childKey), value);
    }

    public static <T> Specification<T> isNull(String key) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(key));
    }

    public static <T> Specification<T> dateEqual(String key, String msgDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.function("to_char", Date.class, root.get(key), criteriaBuilder.literal("YYYY-MM-dd")), msgDate);
    }

    public static <T> Specification<T> dateGreaterThanEqual(String key, String fromDate) throws ParseException {

        LocalDate localDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date parsedDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(key), parsedDate);

    }
    public static <T> Specification<T> dateLessThanEqual(String key, String toDate)  throws ParseException {
        LocalDate localDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date parsedDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(key), parsedDate);
    }
}
