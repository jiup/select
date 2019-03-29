package com.courscio.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jiupeng Zhang
 * @since 03/07/2019
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.findById(id);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean add(User user) {
        return userRepository.insert(user) > 0;
    }

    public boolean updateToken(User user) {
        return userRepository.updateTokenById(user.getId(), user.getPassword()) > 0;
    }

    public boolean updatePartialFields(User user) {
        return userRepository.updateById(user) > 0;
    }
}
