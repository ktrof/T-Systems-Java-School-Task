package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.RegistrationFormDto;
import org.tsystems.javaschool.repository.UserRepository;

import javax.validation.Valid;

/**
 * @author Trofim Kremen
 */
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @ModelAttribute("user")
    public RegistrationFormDto userDto() {
        return RegistrationFormDto.builder().build();
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

}
