package com.courscio.api.cart;

import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@RestController("cartController")
@RequestMapping("/user")
public class CartController {
    private static final Logger LOG = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}/cartItems")
    @PreAuthorize("authentication.principal.get(\"id\").toString().equals(\"\" + #userId)")
    public ResponseEntity<List<CartItem>> get(@PathVariable Long userId) {
        return new ResponseEntity<>(cartService.listByUserId(userId), HttpStatus.OK);
    }


    @PostMapping("/{userId}/cart")
    @PreAuthorize("authentication.principal.get(\"id\").toString().equals(\"\" + #userId)")
    public HttpStatus post(@ModelAttribute CartItem cartItem, @PathVariable Long userId) {
        if (cartItem.getType() == CartItem.Type.wishlist || cartService.checkValidReserve(cartItem.getCourseId(), userId)) {
            if (cartService.addCartItem(cartItem)) {
                return HttpStatus.OK;
            }
            LOG.warn("bad-request: unknown issue when adding cart item (courseId={}, userId={})", cartItem.getCourseId(), userId);
            return HttpStatus.BAD_REQUEST;
        }
        LOG.warn("not-acceptable: conflict exists when adding cart item (courseId={}, userId={})", cartItem.getCourseId(), userId);
        return HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/{userId}/cart/{id}")
    @PreAuthorize("authentication.principal.get(\"id\").toString().equals(\"\" + #userId)")
    public HttpStatus put(@PathVariable Long id, CartItem.Type type, @PathVariable Long userId) {
        CartItem item = cartService.getItem(id);
        if (!cartService.checkValidReserve(item.getCourseId(), userId) && type == CartItem.Type.reserved) {
            LOG.warn("bad-request: invalid reservation (courseId={}, userId={})", item.getCourseId(), userId);
            return HttpStatus.BAD_REQUEST;
        }
        return cartService.updateCartItemType(id, type) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @Delete("/{userId}/cart/{id}")
    @PreAuthorize("authentication.principal.get(\"id\").toString().equals(\"\" + #userId)")
    public HttpStatus delete(@PathVariable Long id, @PathVariable Long userId) {
        if (cartService.deleteCartItem(id)) {
            return HttpStatus.OK;
        }
        CartItem item = cartService.getItem(id);
        LOG.warn("bad-request: invalid cart delete (courseId={}, userId={})", item.getCourseId(), userId);
        return HttpStatus.BAD_REQUEST;
    }
}
