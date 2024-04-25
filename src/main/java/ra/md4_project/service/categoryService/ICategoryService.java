package ra.md4_project.service.categoryService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.md4_project.exception.CategoryNotEmpty;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.request.AddCategoryRequest;
import ra.md4_project.model.dto.request.EditCategoryRequest;
import ra.md4_project.model.entity.Category;

import java.util.List;

public interface ICategoryService {
    Page<Category> getAll(Pageable pageable);
    List<Category>seach(String key);
    Category findbyId(Long categoryId) throws DataNotFound;

    Category addCategory(AddCategoryRequest categoryRequest);
    Category editCategory(EditCategoryRequest editCategoryRequest,Long categoryId) throws DataNotFound;
     void deleteById(Long categoryId) throws DataNotFound, CategoryNotEmpty;
}
