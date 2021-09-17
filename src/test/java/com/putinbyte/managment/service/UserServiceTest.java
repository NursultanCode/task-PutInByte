package com.putinbyte.managment.service;

import com.putinbyte.managment.model.User;
import com.putinbyte.managment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String email = "test@test.test";

    @BeforeEach
    void setUp(){
        userService = new UserService(userRepository, encoder);
        userService.saveUser(User.builder().email(email).password("12345").id(3L).name("test").build());
    }

    @Test
    void findUserByEmail() {
        User user = userService.findUserByEmail(email);
        assertEquals(email, user.getEmail());
    }


    @Test
    void findAllUsers() {
        List<User> users = userService.findAllUsers();
        assertEquals(3,users.size());
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(3L);
        List<User> users = userService.findAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void findUserById() {
        User user = userService.findUserById(1L);
        assertEquals(1L, user.getId());
    }
}