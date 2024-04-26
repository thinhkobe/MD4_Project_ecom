package ra.md4_project.service.oderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.request.OrderRequest;
import ra.md4_project.model.entity.Order;
import ra.md4_project.model.entity.OrderDetail;
import ra.md4_project.model.entity.ShoppingCart;
import ra.md4_project.repository.IOrderDetailRepository;
import ra.md4_project.repository.IOrderRepository;
import ra.md4_project.repository.IShoppingCartRepository;
import ra.md4_project.repository.IUserRepository;
import ra.md4_project.service.userService.IUserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService{
    @Autowired
    private IUserService userService;
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;



}
