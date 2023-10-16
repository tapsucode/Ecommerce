package com.vananh.ecommerce.service.implementation;

import com.vananh.ecommerce.config.JwtProvider;
import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.repository.UserRepository;
import com.vananh.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userService;
    private JwtProvider jwtProvider;
    @Override
    public User findUserById(Long userId) throws UserException {

        Optional<User> user = userService.findById(userId);

        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with id - "+userId);

    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email = jwtProvider.getEmailFromToken(jwt);

        User user = userService.findByEmail(email);

        if (user == null) {
            throw new UserException("User not found with email "+email);
        }
        return user;
    }
}
