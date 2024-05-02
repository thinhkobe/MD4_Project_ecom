package ra.md4_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.md4_project.model.entity.Product;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByProductNameContainsOrDescriptionContains(String product,String description);

    @Query(value = "select * from products p order by p.created_at desc limit :number",nativeQuery = true)
    List<Product>getTopNewProduct(@Param("number")Integer number);
    List<Product>getProductsByCategoryCategoryId(Long id);

    Page<Product> findByStatusTrue(Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM products WHERE LOWER(product_name) = LOWER(:productName) AND product_id = :productId"
            , nativeQuery = true)
    int countByProductNameIgnoreCaseAndProductIdNot(@Param("productName") String productName, @Param("productId") Long productId);

    boolean existsByProductName(String productName);


}
