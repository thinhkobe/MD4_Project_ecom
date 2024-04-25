package ra.md4_project.service.shoppingCartService;

import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.request.AddShoppingCartRequest;
import ra.md4_project.model.dto.response.ShoppingCartResponse;
import ra.md4_project.model.entity.ShoppingCart;

import java.util.List;

public interface IShopPingCartService {
    List<ShoppingCartResponse>getAllShoppingCartByUserId(Long userId);
    ShoppingCartResponse doAddShoppingCart(AddShoppingCartRequest shopPingCartResponse, Long userId) throws DataNotFound;
}
