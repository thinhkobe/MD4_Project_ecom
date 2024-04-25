package ra.md4_project.service.productService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.md4_project.exception.DataExist;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.request.EditProductRequest;
import ra.md4_project.model.dto.request.ProductRequest;
import ra.md4_project.model.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> searchProduct (String searchKey);
    Page<Product> findAll(Pageable pageable);
    List<Product> getTopNewProduct(Integer number);
    List<Product> findProductByCategoryId(Long categoryId);
    Product findById(Long id) throws DataNotFound;
    Page<Product> findAllProductActive(Pageable pageable);

    Product addProduct(ProductRequest productRequest) throws DataNotFound;

    Product updateProduct(Long productId, EditProductRequest editProductRequest) throws DataNotFound, DataExist;

    void deleteProduct(Long productId) throws DataNotFound;

}
