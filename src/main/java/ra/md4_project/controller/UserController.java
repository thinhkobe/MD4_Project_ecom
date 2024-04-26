package ra.md4_project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ra.md4_project.exception.DataExist;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.exception.RequestError;
import ra.md4_project.model.dto.request.AddShoppingCartRequest;
import ra.md4_project.model.dto.request.ChangePassWordDOT;
import ra.md4_project.model.dto.request.FormUpdateUser;
import ra.md4_project.model.dto.request.OrderRequest;
import ra.md4_project.model.dto.respornWapper.EHttpStatus;
import ra.md4_project.model.dto.respornWapper.RespronWapper;
import ra.md4_project.security.principle.UserDetailsCustom;
import ra.md4_project.service.oderService.IOrderService;
import ra.md4_project.service.shoppingCartService.IShopPingCartService;
import ra.md4_project.service.userService.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IShopPingCartService iShopPingCartService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IUserService userService;

    @GetMapping("/cart/list")
    private ResponseEntity<?> getCartList(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iShopPingCartService.getAllShoppingCartByUserId(userDetailsCustom.getId())
        ), HttpStatus.OK);

    }

    @PostMapping("/cart/add")
    private ResponseEntity<?> addToCard(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom, @Valid @RequestBody AddShoppingCartRequest shoppingCartResponse) throws DataNotFound, RequestError {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                iShopPingCartService.doAddShoppingCart(shoppingCartResponse, userDetailsCustom.getId())
        ), HttpStatus.OK);
    }


    @PutMapping("/cart/items/{cartItemId}")
    private ResponseEntity<?> editProductQuantity(@RequestParam Integer quantity, @PathVariable Long cartItemId) throws DataNotFound {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.CREATED.name(),
                HttpStatus.CREATED.value(),
                iShopPingCartService.editProductQuantity(cartItemId, quantity)
        ), HttpStatus.OK);
    }


    @DeleteMapping("/cart/items/{cartItemId}")
    private ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId) throws DataNotFound {
        iShopPingCartService.deleteCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cart/clear")
    private ResponseEntity<?> deleteCartByUserLogin(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom) throws DataNotFound {
        iShopPingCartService.deleteAllCartByUserLoginId(userDetailsCustom.getId());
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/cart/checkout")
    private ResponseEntity<?> checkout(@Valid @RequestBody OrderRequest orderRequest, @AuthenticationPrincipal UserDetailsCustom userDetailsCustom) throws DataNotFound, RequestError {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iShopPingCartService.checkOut(userDetailsCustom.getId(), orderRequest)
        ), HttpStatus.OK);
    }


    @GetMapping("/account")
    private ResponseEntity<?> userInfo( @AuthenticationPrincipal UserDetailsCustom userDetailsCustom) throws DataNotFound, RequestError {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                userService.getInfo(userDetailsCustom.getId())
        ), HttpStatus.OK);
    }

    @PutMapping("/account")
    private ResponseEntity<?> updateUserInfo(@Valid @RequestBody FormUpdateUser formUpdateUser, @AuthenticationPrincipal UserDetailsCustom userDetailsCustom) throws DataNotFound, DataExist {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                userService.updateInfo(userDetailsCustom.getId(),formUpdateUser)
        ), HttpStatus.OK);
    }

    @PutMapping("/account/change-password")
    private ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassWordDOT changePassWordDOT, @AuthenticationPrincipal UserDetailsCustom userDetailsCustom) throws DataNotFound, DataExist {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                userService.changePassWord(userDetailsCustom.getId(),changePassWordDOT)
        ), HttpStatus.OK);
    }


}
