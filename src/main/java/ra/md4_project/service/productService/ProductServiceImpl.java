package ra.md4_project.service.productService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.md4_project.exception.DataExist;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.request.EditProductRequest;
import ra.md4_project.model.dto.request.ProductRequest;
import ra.md4_project.model.entity.Category;
import ra.md4_project.model.entity.Product;
import ra.md4_project.repository.ICategoryRepository;
import ra.md4_project.repository.IProductRepository;

import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Product> searchProduct(String searchKey) {
        return iProductRepository.findAllByProductNameContainsOrDescriptionContains(searchKey, searchKey);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return iProductRepository.findAll(pageable);
    }

    @Override
    public List<Product> getTopNewProduct(Integer number) {
        return iProductRepository.getTopNewProduct(number);
    }

    @Override
    public List<Product> findProductByCategoryId(Long categoryId) {
        return iProductRepository.getProductsByCategoryCategoryId(categoryId);
    }

    @Override
    public Product findById(Long id) throws DataNotFound {
        return iProductRepository.findById(id).orElseThrow(() -> new DataNotFound("product not found"));
    }

    @Override
    public Page<Product> findAllProductActive(Pageable pageable) {
        return iProductRepository.findByStatusTrue(pageable);
    }

    @Override
    public Product addProduct(ProductRequest productRequest) throws DataNotFound {
        // Tạo instance của Product bằng cách sử dụng Builder Pattern
        Product product = Product.builder()
                .sku(UUID.randomUUID().toString())
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .unitPrice(productRequest.getUnitPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .image(productRequest.getImage())
                .status(true)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        // Kiểm tra và set category nếu có
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new DataNotFound("Category not found"));
            product.setCategory(category);
        }

        // Lưu sản phẩm mới và trả về
        return iProductRepository.save(product);
    }


    public Product updateProduct(Long productId, EditProductRequest productRequest) throws DataNotFound, DataExist {
        // Tìm sản phẩm theo ID
        Product product = iProductRepository.findById(productId)
                .orElseThrow(() -> new DataNotFound("Product not found with id: " + productId));

        // Kiểm tra nếu tên mới không trùng với tên hiện tại của sản phẩm
        if (!Objects.equals(product.getProductName(), productRequest.getProductName())) {
            // Kiểm tra xem tên mới đã tồn tại chưa
            if (iProductRepository.existsByProductName(productRequest.getProductName())) {
                throw new DataExist("Product name already exists");
            }
            // Cập nhật tên sản phẩm
            product.setProductName(productRequest.getProductName());
        }

        // Cập nhật thông tin sản phẩm
        Product updatedProduct = Product.builder()
                .productId(productId)
                .sku(product.getSku())
                .productName(product.getProductName())
                .description(productRequest.getDescription())
                .unitPrice(productRequest.getUnitPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .image(productRequest.getImage())
                .createdAt(product.getCreatedAt())
                .updatedAt(new Date())
                .build();

        // Kiểm tra xem categoryId có tồn tại không
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new DataNotFound("Category not found with id: " + productRequest.getCategoryId()));
            product.setCategory(category);
        }

        // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu và trả về
        return iProductRepository.save(updatedProduct);
    }


    @Override
    public void deleteProduct(Long productId) throws DataNotFound {
        findById(productId);
        iProductRepository.deleteById(productId);
    }
}
