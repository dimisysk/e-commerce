package gr.aueb.cf.myproject.service;

import gr.aueb.cf.myproject.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.myproject.core.filters.Paginated;
import gr.aueb.cf.myproject.core.filters.ProductFilters;
import gr.aueb.cf.myproject.core.specifications.ProductSpecification;
import gr.aueb.cf.myproject.dto.ProductInsertDTO;
import gr.aueb.cf.myproject.dto.ProductReadOnlyDTO;
import gr.aueb.cf.myproject.mapper.ProductMapper;
import gr.aueb.cf.myproject.model.Customer;
import gr.aueb.cf.myproject.model.Product;
import gr.aueb.cf.myproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;

    }

    @Transactional
    public ProductReadOnlyDTO createProduct(ProductInsertDTO productInsertDTO) {


        Product product = productMapper.mapToProductEntity(productInsertDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.mapToProductReadOnlyDTO(savedProduct);
    }

    @Transactional
    public ProductReadOnlyDTO updateProduct(Long id, ProductInsertDTO productInsertDTO) throws AppObjectNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("Product", "Product not found with ID: " + id));

        // Ενημέρωση όλων των πεδίων του Product που μπορούν να αλλάξουν
        product.setName(productInsertDTO.getName());
        product.setDescription(productInsertDTO.getDescription());
        product.setCategory(productInsertDTO.getCategory());
        product.setBrand(productInsertDTO.getBrand());
        product.setIsAvailable(productInsertDTO.getIsAvailable());
        product.setQuantity(productInsertDTO.getQuantity());
        product.setPrice(productInsertDTO.getPrice());
        product.setImageName(productInsertDTO.getImageName());
        // Εικόνα (imageData) αν υπάρχει στο DTO
        if (productInsertDTO.getImageData() != null) {
            product.setImageData(productInsertDTO.getImageData());
        }

        Product updatedProduct = productRepository.save(product);
        return productMapper.mapToProductReadOnlyDTO(updatedProduct);
    }


    @Transactional
    public void deleteProduct(Long productId) throws AppObjectNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppObjectNotFoundException("Product", "Product not found with ID: " + productId));
        productRepository.delete(product);
    }

    public ProductReadOnlyDTO getProductById(Long productId) throws AppObjectNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppObjectNotFoundException("Product", "Product not found with ID: " + productId));
        return productMapper.mapToProductReadOnlyDTO(product);
    }

    public List<ProductReadOnlyDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapToProductReadOnlyDTO)
                .toList();
    }

    @Transactional
    public Page<ProductReadOnlyDTO> getPaginatedProducts(int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return productRepository.findAll(pageable)
                .map(productMapper::mapToProductReadOnlyDTO);
    }

    @Transactional
    public Paginated<ProductReadOnlyDTO> getProductsFilteredPaginated(ProductFilters filters) {
        var filtered = productRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
        return new Paginated<>(filtered.map(productMapper::mapToProductReadOnlyDTO));
    }

    @Transactional
    public List<ProductReadOnlyDTO> getProductsFiltered(ProductFilters filters) {
        return productRepository.findAll(getSpecsFromFilters(filters))
                .stream()
                .map(productMapper::mapToProductReadOnlyDTO)
                .toList();
    }

    @Transactional
    public Page<ProductReadOnlyDTO> getSortedProducts(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable)
                .map(productMapper::mapToProductReadOnlyDTO);
    }

    private Specification<Product> getSpecsFromFilters(ProductFilters filters) {
        return Specification
                .where(ProductSpecification.productStringFieldLike("brand", filters.getBrand()))
                .and(ProductSpecification.productStringFieldLike("category", filters.getCategory()))
                .and(ProductSpecification.productPriceIsGreaterThan(filters.getMinPrice())
                .and(ProductSpecification.productPriceIsLessThan(filters.getMaxPrice()))
                .and(ProductSpecification.productIsAvailable(filters.getIsAvailable())));
    }


}

