package project.community.theatre.service;

import project.community.theatre.model.UserEntity;

public interface UserService {
    UserEntity authenticateUser(String email, String password);
    UserEntity registerUser(UserEntity user);
    UserEntity getUserById(int userId);
}