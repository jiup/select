package com.courscio.api.cart;

import com.courscio.api.course.CourseRepository;
import com.courscio.api.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Schedule> getSchedule (Long id){
        return cartRepository.getSchedule(id);
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

    public boolean checkValidReserve(Long cart_id, Long userId){
        List<Schedule> newSches = getSchedule(cart_id);
        List<CartItem> userCartList = cartRepository.listByUserId(userId);
        if (userCartList == null) {
            return true;
        }
        for (CartItem c: userCartList) {
            if (c.getType() == CartItem.Type.wishlist) {
                continue;
            }
            List<Schedule> sche = getSchedule (c.getId());
            for (Schedule s1: newSches){
                for (Schedule s2: sche) {
                    if (isConflict(s1, s2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isConflict(Schedule s1, Schedule s2) {
        if (s1.getWeekDay() != s2.getWeekDay()) {
            return false;
        }
        int start_t1 = Integer.parseInt(s1.getStart_t());
        int end_t1 = Integer.parseInt(s1.getEnd_t());
        int start_t2 = Integer.parseInt(s2.getStart_t());
        int end_t2 = Integer.parseInt(s2.getEnd_t());

        if (start_t2 >= start_t1 && start_t2 <= end_t1) {
            return true;
        }
        if (end_t2 >= start_t1 && end_t2 <= end_t1) {
            return true;
        }

        if (start_t1 >= start_t2 && start_t1 <= end_t2) {
            return true;
        }

        if (end_t1 >= start_t2 && end_t1 <= end_t2) {
            return true;
        }
        return false;
    }
}
