package com.roaa.service;

import com.roaa.exception.UserException;
import com.roaa.modal.User;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id) throws UserException;

    List<User> getAllUsers();

    String deleteUser(Long id) throws UserException;

    User updateUser(Long id, User user) throws UserException;
}
