package ra.md4_project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ra.md4_project.exception.CategoryNotEmpty;
import ra.md4_project.exception.DataExist;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.request.AddCategoryRequest;
import ra.md4_project.model.dto.request.EditCategoryRequest;
import ra.md4_project.model.dto.request.EditProductRequest;
import ra.md4_project.model.dto.request.ProductRequest;
import ra.md4_project.model.dto.respornWapper.EHttpStatus;
import ra.md4_project.model.dto.respornWapper.RespronWapper;
import ra.md4_project.repository.ICategoryRepository;
import ra.md4_project.service.categoryService.ICategoryService;
import ra.md4_project.service.productService.IProductService;
import ra.md4_project.service.roleService.IRoleService;
import ra.md4_project.service.userService.IUserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(@PageableDefault(page = 0, size = 5, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iUserService.findAll(pageable)
        ), HttpStatus.OK);

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserByUserId(@PathVariable Long id) throws DataNotFound {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iUserService.findById(id)
        ), HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> changeUserStatusByUserId(@PathVariable Long userId) throws DataNotFound {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iUserService.changeUserStatusByUserId(userId)
        ), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iRoleService.getAll()
        ), HttpStatus.OK);
    }

    @GetMapping("/user/search")
    public ResponseEntity<?> searchUser(@RequestParam(name = "searchKey") String searchKey) {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iUserService.searchUserByName(searchKey)
        ), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProduct(@PageableDefault(page = 0, size = 5, sort = "productId", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iProductService.findAll(pageable)
        ), HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> findProductByProductId(@PathVariable Long productId) throws DataNotFound {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iProductService.findById(productId)
        ), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<?> doAddProduct(@Valid @RequestBody ProductRequest productRequest) throws DataNotFound {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                iProductService.addProduct(productRequest)
        ), HttpStatus.OK);
    }
  @PutMapping("/products/{productId}")
    public ResponseEntity<?> doEditProduct(@Valid @RequestBody EditProductRequest editProductRequest, @PathVariable Long productId) throws DataNotFound, DataExist {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                iProductService.updateProduct(productId,editProductRequest)
        ), HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) throws DataNotFound, DataExist {
        iProductService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<?>getAllCategory(@PageableDefault(page = 0, size = 5, sort = "categoryName", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iCategoryService.getAll(pageable)
        ), HttpStatus.OK);
    }
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?>getCategoryById(@PathVariable Long categoryId) throws DataNotFound {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iCategoryService.findbyId(categoryId)
        ), HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<?>addCategory(@Valid @RequestBody AddCategoryRequest categoryRequest) throws DataNotFound {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                iCategoryService.addCategory(categoryRequest)
        ), HttpStatus.OK);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<?>editCategory(@Valid @RequestBody EditCategoryRequest editCategoryRequest,@PathVariable Long categoryId) throws DataNotFound {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iCategoryService.editCategory(editCategoryRequest,categoryId)
        ), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?>deleteCategory(@PathVariable Long categoryId) throws DataNotFound, CategoryNotEmpty {
        iCategoryService.deleteById(categoryId);
         return ResponseEntity.noContent().build();
    }

}
