package com.putinbyte.managment;

import com.putinbyte.managment.model.RoleNames;
import com.putinbyte.managment.model.User;
import com.putinbyte.managment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

    @Component
    public class InitialSetup {

        @Autowired
        private UserService userService;

        @Value("admin")
        private String name;

        @Value("admin@gmail.com")
        private String email;

        @Value("admin")
        private String password;



        @PostConstruct
        public void initIt() throws Exception {

            User dbUser = userService.findUserByEmail(email);

            if (dbUser == null) {
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setActive(Boolean.TRUE);
                user.setRoleName(RoleNames.ADMIN.name());
                userService.saveUser(user);
            }
        }

    }
