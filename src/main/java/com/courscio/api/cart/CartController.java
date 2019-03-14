package com.courscio.api.cart;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@RestController("cartController")
@RequestMapping("/user/{userId}/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> get(@PathVariable Long userId) {
        return new ResponseEntity<>(cartService.listByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public HttpStatus post(@ModelAttribute CartItem cartItem) {
        return cartService.addCartItem(cartItem) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/{id}")
    public HttpStatus put(@PathVariable Long id, CartItem.Type type) {
        return cartService.updateCartItemType(id, type) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @Delete("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        return cartService.deleteCartItem(id) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
}
