package com.putinbyte.managment.controller;

import com.putinbyte.managment.model.NewPasswordDto;
import com.putinbyte.managment.model.RoleNames;
import com.putinbyte.managment.model.User;
import com.putinbyte.managment.service.UserService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String getAllUsers(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<User> users = userService.findAllUsers();
        users.removeIf(user -> user.getEmail().equals(auth.getName()));
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("admin/addUser")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("admin/addUser")
    public String processAddUser(@Valid User user, BindingResult result){
        if (result.hasErrors()){
            return "userForm";
        }else {
            user.setActive(true);
            userService.saveUser(user);
            return "redirect:/";
        }
    }
    @GetMapping("admin/user/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return "redirect:/";
    }

    @GetMapping("admin/user/update/{id}")
    public String updateUser(@PathVariable Long id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user",user);
        return "updateForm";
    }

    @GetMapping("edit")
    public String updateAdmin(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        return "updateFormAdmin";
    }

    @GetMapping("changePassword")
    public String changePasswrod(Model model){
        model.addAttribute("newPasswordDto",new NewPasswordDto());
        return "changePassword";
    }

    @PostMapping("changePassword")
    public String changePasswordProcess(@Valid NewPasswordDto passwordDto,BindingResult result){
        if (result.hasErrors()){
            return "changePassword";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(passwordDto.getPassword()));
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("admin")
    public String getMyProfile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        return "profile";
    }
    @PostMapping("edit")
    public String updateUserProcess(@Valid User user, BindingResult result){
        if (result.hasErrors()){
            return "updateFormAdmin";
        }else {
            Collection<SimpleGrantedAuthority> nowAuthorities =(Collection<SimpleGrantedAuthority>)SecurityContextHolder
                    .getContext().getAuthentication().getAuthorities();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), nowAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            user.setActive(true);
            userService.saveUser(user);
            return "redirect:/";
        }
    }

    @PostMapping("admin/user/update/{id}")
    public String updateUserProcess(@PathVariable Long id,@Valid User user, BindingResult result){
        if (result.hasErrors()){
            return "updateForm";
        }else {
            user.setActive(true);
            userService.saveUser(user);
            return "redirect:/";
        }
    }




}
