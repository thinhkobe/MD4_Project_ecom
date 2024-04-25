package ra.md4_project.service.categoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.md4_project.exception.CategoryNotEmpty;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.request.AddCategoryRequest;
import ra.md4_project.model.dto.request.EditCategoryRequest;
import ra.md4_project.model.entity.Category;
import ra.md4_project.repository.ICategoryRepository;
import ra.md4_project.repository.IProductRepository;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> seach(String key) {
        return categoryRepository.searchByKeyword(key);
    }

    @Override
    public Category findbyId(Long categoryId) throws DataNotFound {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFound("Category Id not found"));
    }

    @Override
    public Category addCategory(AddCategoryRequest categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getDescription());
        category.setStatus(true);
        return categoryRepository.save(category);
    }

    @Override
    public Category editCategory(EditCategoryRequest editCategoryRequest,Long categoryId) throws DataNotFound {
        Category category=findbyId(categoryId);
        if (editCategoryRequest.getCategoryName()!=null&&!editCategoryRequest.getCategoryName().isBlank()){
            category.setCategoryName(editCategoryRequest.getCategoryName());
        }
        if (editCategoryRequest.getDescription()!=null&&!editCategoryRequest.getDescription().isBlank()){
            category.setDescription(editCategoryRequest.getDescription());
        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long categoryId) throws DataNotFound, CategoryNotEmpty {
        if (!iProductRepository.getProductsByCategoryCategoryId(categoryId).isEmpty()){
            throw new CategoryNotEmpty("Category Not Empty");
        }
        categoryRepository.delete(findbyId(categoryId));
    }
}
