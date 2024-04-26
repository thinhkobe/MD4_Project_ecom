package ra.md4_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.md4_project.model.dto.respornWapper.EHttpStatus;
import ra.md4_project.model.dto.respornWapper.RespronWapper;
import ra.md4_project.service.categoryService.ICategoryService;

@Controller
@RequestMapping("/md4_project.com/v1/categoris")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping()
    public ResponseEntity<?> getAll(@PageableDefault(page = 0, size = 3, sort = "productName", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iCategoryService.getAll(pageable)
        ), HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCategory(@RequestParam(name = "seachkey") String seachkey) {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iCategoryService.seach(seachkey)
        ), HttpStatus.OK);

    }


}
