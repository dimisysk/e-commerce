package gr.aueb.cf.myproject.core.specifications;

import gr.aueb.cf.myproject.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public ProductSpecification() {}

    public static Specification<Product> productNameIs(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Product> productCategoryIs(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null || category.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(root.get("category"), "%" + category + "%");
        };
    }

    public static Specification<Product> productIsAvailable(Boolean isAvailable) {
        return (root, query, criteriaBuilder) -> {
            if (isAvailable == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("isAvailable"), isAvailable);
        };
    }

    public static Specification<Product> productPriceIsLessThan(Float price) {
        return (root, query, criteriaBuilder) -> {
            if (price == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.lessThan(root.get("price"), price);
        };
    }

    public static Specification<Product> productPriceIsGreaterThan(Float price) {
        return (root, query, criteriaBuilder) -> {
            if (price == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
        };
    }

}
