package gr.aueb.cf.myproject.core.specifications;

import gr.aueb.cf.myproject.core.enums.GenderType;
import gr.aueb.cf.myproject.model.Admin;
import gr.aueb.cf.myproject.model.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class AdminSpecification {

    public AdminSpecification() {}

    public static Specification<Admin> adminUserSsnIs(String ssn) {
        return (root, query, criteriaBuilder ) -> {
            if (ssn == null || ssn.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Admin, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("ssn"), ssn);
        };
    }


    public static Specification<Admin> adminUserIsActive(Boolean isActive) {
        return ((root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Admin, User> user = root.join("user");
            return criteriaBuilder.equal(user.get("isActive"), isActive);
        });
    }


    public static Specification<Admin> adminUserGenderIs(GenderType gender) {
        return (root, query, criteriaBuilder ) -> {
            if (gender == null ) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Admin, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("gender"), gender);
        };
    }

    public static Specification<Admin> adminStringFieldLike(String field, String value) {
        return ((root, query, criteriaBuilder) -> {
            if (value == null || value.trim().isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%" + value.toUpperCase() + "%");
        });
    }

}
