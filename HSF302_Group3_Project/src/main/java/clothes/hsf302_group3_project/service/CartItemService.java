package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.entity.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByIdsAndUserId(List<Long> ids, Long userId);
}
