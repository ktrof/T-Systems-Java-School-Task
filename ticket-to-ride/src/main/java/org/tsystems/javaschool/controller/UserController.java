package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.ticket.TicketDto;
import org.tsystems.javaschool.model.dto.user.UpdateUserFormDto;
import org.tsystems.javaschool.model.dto.user.UserDto;
import org.tsystems.javaschool.service.TicketService;
import org.tsystems.javaschool.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * @author Trofim Kremen
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;

    @GetMapping(value = "/login")
    public String getLoginForm(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute("previousUrl", referer);
        return "login";
    }

    @GetMapping(value = "/profile")
    public String getUserProfile(Model model, Principal principal) {
        UserDto userDto = userService.getByLogin(principal.getName());
        UpdateUserFormDto updateUserFormDto = UpdateUserFormDto.builder()
                .login(userDto.getLogin())
                .birthDate(userDto.getBirthDate())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .secondName(userDto.getSecondName())
                .mobileNumber(userDto.getMobileNumber())
                .build();
        List<TicketDto> ticketDtoList = ticketService.getByUserLogin(principal.getName());
        model.addAttribute("userDetails", userService.getByLogin(principal.getName()));
        model.addAttribute("userToEdit", updateUserFormDto);
        model.addAttribute("tickets", ticketDtoList);
        return "profile";
    }

    @PostMapping(value = "/profile/edit")
    public String editUserProfile(@ModelAttribute("userToEdit") @Valid UpdateUserFormDto updateUserFormDto,
                                  BindingResult bindingResult, Model model, Principal principal) {
        model.addAttribute("userDetails", userService.getByLogin(principal.getName()));
        if (Objects.nonNull(userService.getByLogin(updateUserFormDto.getLogin()))) {
            bindingResult.rejectValue("login", null, "There is already a user with that login");
        }
        if (Objects.nonNull(userService.getByEmail(updateUserFormDto.getEmail()))) {
            bindingResult.rejectValue("email", null, "There is already a user with that email");
        }
        if(bindingResult.hasErrors()) {
            return "profile";
        }
        userService.editUser(updateUserFormDto);
        return "profile";
    }

}
