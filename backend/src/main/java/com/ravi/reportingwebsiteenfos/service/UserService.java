package com.ravi.reportingwebsiteenfos.service;

import com.ravi.reportingwebsiteenfos.model.User;
import com.ravi.reportingwebsiteenfos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
