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
@RequestMapping("/user")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}/cartItems")
    public ResponseEntity<List<CartItem>> get(@PathVariable Long userId) {
        return new ResponseEntity<>(cartService.listByUserId(userId), HttpStatus.OK);
    }


    @PostMapping("/{userId}/cart")
    public HttpStatus post(@ModelAttribute CartItem cartItem, @PathVariable Long userId) {
        if (cartItem.getType() == CartItem.Type.wishlist || cartService.checkValidReserve(cartItem.getCourseId(), userId)) {
            return  cartService.addCartItem(cartItem) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/{userId}/cart/{id}")
    public HttpStatus put(@PathVariable Long id, CartItem.Type type, @PathVariable Long userId) {
        CartItem item = cartService.getItem(id);
        if (!cartService.checkValidReserve(item.getCourseId(), userId) && type == CartItem.Type.reserved) {
            return HttpStatus.BAD_REQUEST;
        }
        return cartService.updateCartItemType(id, type) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @Delete("/{userId}/cart/{id}")
    public HttpStatus delete(@PathVariable Long id, @PathVariable Long userId) {
        return cartService.deleteCartItem(id) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
}
