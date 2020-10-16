package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.TicketDto;
import org.tsystems.javaschool.model.dto.UserDto;
import org.tsystems.javaschool.repository.UserRepository;
import org.tsystems.javaschool.service.TicketService;
import org.tsystems.javaschool.service.UserService;

import javax.servlet.http.HttpServletRequest;
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
        List<TicketDto> ticketDtoList = ticketService.getByUserLogin(principal.getName());
        model.addAttribute("userDetails", userService.getByLogin(principal.getName()));
        model.addAttribute("tickets", ticketDtoList);
        return "profile";
    }

    @PostMapping(value = "/profile/edit")
    public String editUserProfile(BindingResult bindingResult, Model model, Principal principal) {
        UserDto editingUserDto = userService.getByLogin(principal.getName());
        if (Objects.nonNull(userService.getByLogin(editingUserDto.getLogin()))) {
            bindingResult.reject("login", null, "There is already a user with that login");
        }
        if (Objects.nonNull(userService.getByEmail(editingUserDto.getEmail()))) {
            bindingResult.reject("email", null, "There is already a user with that email");
        }
        model.addAttribute("userDetails", editingUserDto);

        return "profile";
    }

}
