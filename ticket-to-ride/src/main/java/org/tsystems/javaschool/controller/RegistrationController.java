package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tsystems.javaschool.model.dto.RegistrationFormDto;
import org.tsystems.javaschool.model.dto.UserDto;
import org.tsystems.javaschool.service.UserService;

import javax.validation.Valid;

/**
 * @author Trofim Kremen
 */
@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @ModelAttribute("user")
    public RegistrationFormDto userDto() {
        return RegistrationFormDto.builder().build();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid RegistrationFormDto userDto,
                                      BindingResult result){

        UserDto existingByLogin = userService.getByLogin(userDto.getLogin());
        if (existingByLogin != null) {
            result.rejectValue("login", null, "There is already an account registered with that login");
        }

        UserDto existingByEmail = userService.getByEmail(userDto.getEmail());
        if (existingByEmail != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.registerUser(userDto);
        return "redirect:/login?success";
    }

}
