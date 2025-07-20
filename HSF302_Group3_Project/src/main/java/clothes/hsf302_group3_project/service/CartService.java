package clothes.hsf302_group3_project.service;

import jakarta.servlet.http.HttpSession;

public interface CartService {
      void handleRemoveCartItem(long id, HttpSession session);
          void handleAddProductToCart(String email, int productId, HttpSession session, long quantity);
}
