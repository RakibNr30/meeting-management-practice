package com.dsi.meeting.controller;

import com.dsi.meeting.dto.MeetingDto;
import com.dsi.meeting.entity.Meeting;
import com.dsi.meeting.entity.User;
import com.dsi.meeting.repository.MeetingRepository;
import com.dsi.meeting.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    public MeetingController(MeetingRepository meetingRepository, UserRepository userRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String index(Model model) {

        List<User> users = userRepository.findAll();
        List<Meeting> meetings = meetingRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("meetings", meetings);

        return "front/meeting/index";
    }

    @PostMapping("/store")
    public String save(@ModelAttribute MeetingDto meetingDto) {

        try {
            Meeting meeting = new Meeting();
            meeting.setTitle(meetingDto.getTitle());
            meeting.setUserIds(meetingDto.getUserIds());
            this.meetingRepository.save(meeting);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "redirect:/meeting";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {

        Meeting meeting = meetingRepository.findById(id).orElse(null);

        if (meeting == null) {
            return "redirect:/meeting";
        }

        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("meeting", meeting);

        return "front/meeting/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute MeetingDto meetingDto, @PathVariable Long id) {

        Meeting meeting = meetingRepository.findById(id).orElse(null);

        if (meeting == null) {
            return "redirect:/meeting";
        }

        try {
            meeting.setTitle(meeting.getTitle());
            meeting.setUserIds(meeting.getUserIds());
            this.meetingRepository.save(meeting);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "redirect:/meeting";
    }

    @PostMapping("/{id}/destroy")
    public String destroy(@PathVariable Long id) {

        try {
            Meeting meeting = meetingRepository.findById(id).orElse(null);

            if (meeting == null) {
                return "redirect:/meeting";
            }

            this.meetingRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "redirect:/meeting";
    }
}
