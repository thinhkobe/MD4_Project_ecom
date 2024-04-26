package ra.md4_project.service.shoppingCartService;

import ra.md4_project.exception.DataNotFound;
import ra.md4_project.exception.RequestError;
import ra.md4_project.model.dto.request.AddShoppingCartRequest;
import ra.md4_project.model.dto.request.OrderRequest;
import ra.md4_project.model.dto.response.OrderDetailDTO;
import ra.md4_project.model.dto.response.OrderResponse;
import ra.md4_project.model.dto.response.ShoppingCartResponse;
import ra.md4_project.model.dto.response.UserInformation;
import ra.md4_project.model.entity.OrderDetail;
import ra.md4_project.model.entity.ShoppingCart;

import java.util.List;

public interface IShopPingCartService {
    List<ShoppingCartResponse> getAllShoppingCartByUserId(Long userId);

    ShoppingCartResponse doAddShoppingCart(AddShoppingCartRequest shopPingCartResponse, Long userId) throws DataNotFound, RequestError;

    ShoppingCartResponse editProductQuantity(Long cartId, Integer newquantity) throws DataNotFound;

    ShoppingCartResponse deleteCart(Long cartId) throws DataNotFound;

    ShoppingCartResponse deleteAllCartByUserLoginId(Long userId) throws DataNotFound;

    OrderResponse checkOut(Long userId, OrderRequest orderRequest) throws DataNotFound, RequestError;


}
