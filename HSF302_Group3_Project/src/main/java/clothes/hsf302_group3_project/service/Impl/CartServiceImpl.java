package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.entity.*;
import clothes.hsf302_group3_project.repository.CartItemRepository;
import clothes.hsf302_group3_project.repository.CartRepository;
import clothes.hsf302_group3_project.repository.ProductRepository;
import clothes.hsf302_group3_project.repository.UserRepository;
import clothes.hsf302_group3_project.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static clothes.hsf302_group3_project.security.utils.SecurityUtil.getCurrentUserEmail;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void handleRemoveCartItem(long id) {
        Optional<CartItem> cartItemOptional = this.cartItemRepository.findById(id);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            Cart cart = cartItem.getCart();
            // delete cartItem
            this.cartItemRepository.deleteById(id);

            int cartSum = cart.getSum();
            if (cartSum > 1) {
                cart.setSum(cartSum - 1);
                this.cartRepository.save(cart);
            } else {
                this.cartRepository.deleteById(cart.getId());
            }
        }
    }

    @Override
    public void handleAddProductToCart(int productId, long quantity) {
        String email = getCurrentUserEmail();
        User user = this.userRepository.findByEmail(email).get();
//        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check User da co Cart chua ? neu chua -> tao moi
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                // tao moi cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = this.cartRepository.save(otherCart);
            }

            // luu cart_detail
            // tim product bang id
            Product productOptional = this.productRepository.findById(productId).orElse(null);

            if (productOptional != null) {
                Product realProduct = productOptional;
                //Check san pham da tung duoc them vao gio hang truoc day chua
                CartItem oldDetail = this.cartItemRepository.findByCartAndProduct(cart, realProduct);
                //
                if (oldDetail == null) {
                    CartItem cd = new CartItem();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(quantity);
                    this.cartItemRepository.save(cd);

                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    this.cartItemRepository.save(oldDetail);
                }
            }
        }
    }

}
