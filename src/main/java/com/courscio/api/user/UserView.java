package com.courscio.api.user;

import org.springframework.beans.BeanUtils;

/**
 * @author Jiupeng Zhang
 * @since 03/25/2019
 */
public class UserView {
    private Long id;
    private String name;
    private String email;
    private String avatar;
    private Boolean beginner;

    public static UserView from(User user) {
        UserView userView = new UserView();
        BeanUtils.copyProperties(user, userView);
        userView.setName(user.getNickname());
        userView.setBeginner(user.isBeginner());
        return userView;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getBeginner() {
        return beginner;
    }

    public void setBeginner(Boolean beginner) {
        this.beginner = beginner;
    }
}
