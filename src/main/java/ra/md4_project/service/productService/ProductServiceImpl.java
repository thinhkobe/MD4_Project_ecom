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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        Product product = new Product();
        product.setSku(UUID.randomUUID().toString());
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImage(productRequest.getImage());
        product.setStatus(true);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());

        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new DataNotFound("Category not found"));
            product.setCategory(category);
        }

        return iProductRepository.save(product);

    }

    public Product updateProduct(Long productId, EditProductRequest productRequest) throws DataNotFound, DataExist {
        Optional<Product> optionalProduct = iProductRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new DataNotFound("Product not found with id: " + productId);
        }
        // Kiểm tra tính duy nhất của tên sản phẩm mới


        int number = iProductRepository.countByProductNameIgnoreCaseAndProductIdNot(productRequest.getProductName(), productId);
        if (number > 0) {
            throw new DataExist("Product name already exists");
        }

        Product product = optionalProduct.get();
        // Cập nhật thông tin sản phẩm từ dữ liệu trong ProductRequest
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImage(productRequest.getImage());
        product.setUpdatedAt(new Date());

        // kiểm tra categoryId có tồn tại hay không
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new DataNotFound("Category not found with id: " + productRequest.getCategoryId()));
            product.setCategory(category);
        }

        // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu và trả về
        return iProductRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) throws DataNotFound {
        findById(productId);
        iProductRepository.deleteById(productId);
    }
}
