package gr.aueb.cf.myproject.core.specifications;

import gr.aueb.cf.myproject.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {



    public static Specification<Product> productBrandIs(String brand) {
        return (root, query, criteriaBuilder) -> {
            if (brand == null || brand.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(root.get("name"), "%" + brand + "%");
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
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
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

    public static Specification<Product> productStringFieldLike(String field, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%" + value.toUpperCase() + "%");
        };
    }

    public static Specification<Product> productWithFilters(String brand, String category, Boolean isAvailable,Float minPrice, Float maxPrice) {
        return Specification.where(ProductSpecification.productBrandIs(brand))
                .and(ProductSpecification.productCategoryIs(category))
                .and(ProductSpecification.productPriceIsGreaterThan(maxPrice))
                .and(ProductSpecification.productIsAvailable(isAvailable))
                .and(ProductSpecification.productPriceIsLessThan(maxPrice));

    }


}
