package com.dsi.meeting.controller;

import com.dsi.meeting.dto.UserDto;
import com.dsi.meeting.entity.User;
import com.dsi.meeting.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);

        return "front/index";
    }

    @PostMapping("/store")
    public String save(@ModelAttribute UserDto userStoreDto) {

        try {
            User user = new User();
            user.setName(userStoreDto.getName());
            this.userRepository.save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "redirect:/";
    }
}
