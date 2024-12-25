package gr.aueb.cf.myproject.core.specifications;

import gr.aueb.cf.myproject.core.enums.Status;
import gr.aueb.cf.myproject.model.Transaction;
import gr.aueb.cf.myproject.model.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransactionSpecification {

    public TransactionSpecification() {}

    public static Specification<Transaction> transactionStatusIs(Status status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null ) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Transaction> transactionAmountGreaterThan(Float amount) {
        return (root, query, criteriaBuilder) -> {
            if (amount == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), amount);
        };
    }

    public static Specification<Transaction> transactionAmountLessThan(Float amount) {
        return (root, query, criteriaBuilder) -> {
            if (amount == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.lessThan(root.get("amount"), amount);
        };
    }

    public static Specification<Transaction> transactionCreatedAtAfter(LocalDateTime createdAt) {
        return (root, query, criteriaBuilder) -> {
            if (createdAt == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.greaterThan(root.get("createdAt"), createdAt);
        };
    }

    public static Specification<Transaction> transactionUserIdIs(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Transaction, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("id"), userId);
        };
    }

    public static Specification<Transaction> transactionWithFilters(Status status, Float minAmount, LocalDateTime afterDate, Long userId) {
        return Specification.where(transactionStatusIs(status))
                .and(transactionAmountGreaterThan(minAmount))
                .and(transactionCreatedAtAfter(afterDate))
                .and(transactionUserIdIs(userId));
    }


}
