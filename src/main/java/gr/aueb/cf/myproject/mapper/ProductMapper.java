package gr.aueb.cf.myproject.mapper;

import gr.aueb.cf.myproject.dto.ProductInsertDTO;
import gr.aueb.cf.myproject.dto.ProductReadOnlyDTO;
import gr.aueb.cf.myproject.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product mapToProductEntity(ProductInsertDTO productInsertDTO) {
        return Product.builder()
                .name(productInsertDTO.getName())
                .description(productInsertDTO.getDescription())
                .category(productInsertDTO.getCategory())
                .brand(productInsertDTO.getBrand())
                .isAvailable(productInsertDTO.getIsAvailable())
                .quantity(productInsertDTO.getQuantity())
                .price(productInsertDTO.getPrice())
//                .imageName(productInsertDTO.getImageName())
                .build();
    }

    public ProductReadOnlyDTO mapToProductReadOnlyDTO(Product product) {
        return ProductReadOnlyDTO.builder()
                .id(product.getId())
//                .uuid(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .brand(product.getBrand())
                .isAvailable(product.getIsAvailable())
                .quantity(product.getQuantity())
                .price(product.getPrice())
//                .imageName(product.getImageName())
                .build();
    }
}
