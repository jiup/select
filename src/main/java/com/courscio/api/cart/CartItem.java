package com.courscio.api.cart;

import org.apache.ibatis.type.EnumTypeHandler;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
public class CartItem implements Serializable {
    private Long id;
    private Long userId;
    private Long courseId;
    private Type type;

    public enum Type {
        wishlist("Wishlist"), reserved("Reserved");

        String displayName;

        Type(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static class Handler extends EnumTypeHandler<Type> {
            public Handler(Class<Type> type) {
                super(type);
            }
        }
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
