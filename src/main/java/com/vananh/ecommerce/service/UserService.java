package com.vananh.ecommerce.service;

import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
