package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.entity.CartItem;
import clothes.hsf302_group3_project.repository.CartItemRepository;
import clothes.hsf302_group3_project.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItem> findByIdsAndUserId(List<Long> ids, Long userId) {
        return cartItemRepository.findByIdInAndCart_User_Id(ids, userId);
    }

}
