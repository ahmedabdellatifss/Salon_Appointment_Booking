package com.roaa.service.imp;

import com.roaa.exception.UserException;
import com.roaa.modal.User;
import com.roaa.repository.UserRepository;
import com.roaa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("User not found");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUser(Long id) throws UserException {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isEmpty()){
            throw new UserException("User not found with id" + id);
        }
        userRepository.delete(opt.get());
        return "User deleted";
    }

    @Override
    public User updateUser(Long id, User user) throws UserException {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isEmpty()){
            throw new UserException("User not found with id" + id);
        }
        User existingUser = opt.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setPhone(user.getPhone());
        return userRepository.save(existingUser);
    }
}
