package com.courscio.api.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartItem> listByUserId(Long userId) {
        return cartRepository.listByUserId(userId);
    }

    public boolean addCartItem(CartItem cartItem) {
        return cartRepository.insert(cartItem) > 0;
    }

    public boolean deleteCartItem(Long id) {
        return cartRepository.deleteById(id) > 0;
    }

    public boolean updateCartItemType(Long id, CartItem.Type type) {
        return cartRepository.updateTypeById(id, type) > 0;
    }
}
