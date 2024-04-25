package ra.md4_project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.request.AddShoppingCartRequest;
import ra.md4_project.model.dto.respornWapper.EHttpStatus;
import ra.md4_project.model.dto.respornWapper.RespronWapper;
import ra.md4_project.security.principle.UserDetailsCustom;
import ra.md4_project.service.shoppingCartService.IShopPingCartService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IShopPingCartService iShopPingCartService;

    @GetMapping("/cart/list")
    private ResponseEntity<?>getCartList(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom){
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iShopPingCartService.getAllShoppingCartByUserId(userDetailsCustom.getId())
        ),HttpStatus.OK);

    }
    @PostMapping("/cart/add")
    private ResponseEntity<?>addToCard(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,@Valid @RequestBody AddShoppingCartRequest shoppingCartResponse) throws DataNotFound {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                iShopPingCartService.doAddShoppingCart(shoppingCartResponse,userDetailsCustom.getId())
        ),HttpStatus.OK);
    }


//    @PostMapping("/cart/items/{cartItemId}")
//    private ResponseEntity<?>editCartQuantity() throws DataNotFound {
//        return new ResponseEntity<>(new RespronWapper<>(
//                EHttpStatus.SUCCESS,
//                HttpStatus.CREATED.name(),
//                HttpStatus.CREATED.value(),
//                iShopPingCartService.doAddShoppingCart(shoppingCartResponse,userDetailsCustom.getId())
//        ),HttpStatus.OK);
//    }


}
