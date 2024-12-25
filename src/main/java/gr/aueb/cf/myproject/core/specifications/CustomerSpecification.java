package gr.aueb.cf.myproject.core.specifications;

import gr.aueb.cf.myproject.core.enums.GenderType;
import gr.aueb.cf.myproject.model.Customer;
import gr.aueb.cf.myproject.model.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public CustomerSpecification() {
    }

    public static Specification<Customer> customerUserSsnIs(String ssn) {
        return (root, query, criteriaBuilder ) -> {
            if (ssn == null || ssn.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Customer, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("ssn"), ssn);
        };
    }


    public static Specification<Customer> customerUserIsActive(Boolean isActive) {
        return ((root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Customer, User> user = root.join("user");
            return criteriaBuilder.equal(user.get("isActive"), isActive);
        });
    }


    public static Specification<Customer> customerUserGenderIs(GenderType gender) {
        return (root, query, criteriaBuilder ) -> {
            if (gender == null ) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Customer, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("gender"), gender);
        };
    }

    public static Specification<Customer> customerStringFieldLike(String field, String value) {
        return ((root, query, criteriaBuilder) -> {
            if (value == null || value.trim().isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%" + value.toUpperCase() + "%");
        });
    }

}
