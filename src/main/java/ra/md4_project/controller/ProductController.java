package ra.md4_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.md4_project.model.dto.respornWapper.EHttpStatus;
import ra.md4_project.model.dto.respornWapper.RespronWapper;
import ra.md4_project.service.productService.IProductService;

@Controller
@RequestMapping("/md4_project.com/v1/products")
public class ProductController {
    @Autowired
    private IProductService iProductService;
    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam(name = "searchKey")String searchKey){
        return new  ResponseEntity<> (new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iProductService.searchProduct(searchKey)
        ),HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<?> getAll(@PageableDefault(page=0,size = 3,sort = "productName",direction = Sort.Direction.ASC)Pageable pageable){
        return new  ResponseEntity<> (new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iProductService.findAllProductActive(pageable)
        ),HttpStatus.OK);

    }
    @GetMapping("/new-product")
    public ResponseEntity<?> getTopNew(@RequestParam(name = "top")Integer number){
        return new  ResponseEntity<> (new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iProductService.getTopNewProduct(number)
        ),HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> findByCategoryId(@PathVariable Long categoryId){
        return new  ResponseEntity<> (new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iProductService.findProductByCategoryId(categoryId)
        ),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new  ResponseEntity<> (new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iProductService.findProductByCategoryId(id)
        ),HttpStatus.OK);
    }
}
