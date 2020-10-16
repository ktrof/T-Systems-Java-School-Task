package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.PassengerFormDto;
import org.tsystems.javaschool.model.dto.RouteDto;
import org.tsystems.javaschool.model.dto.TicketDto;
import org.tsystems.javaschool.model.dto.UserDto;
import org.tsystems.javaschool.service.TicketService;
import org.tsystems.javaschool.service.UserService;

import javax.inject.Provider;
import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

/**
 * @author Trofim Kremen
 */
@Controller
@RequiredArgsConstructor
public class TicketController {

    private final Provider<RouteCache> routeCacheProvider;
    private final TicketService ticketService;
    private final UserService userService;

    @GetMapping(value = "/routes/{id}")
    public String getPassengerForm(@PathVariable("id") UUID id, Model model, Principal principal) {
        RouteDto routeDto = routeCacheProvider.get().findById(id);
        UserDto userDto = userService.getByLogin(principal.getName());
        model.addAttribute("route", routeDto);
        model.addAttribute("passenger", PassengerFormDto.builder()
                .userLogin(userDto.getLogin())
                .firstName(userDto.getFirstName())
                .secondName(userDto.getSecondName())
                .birthDate(userDto.getBirthDate())
                .email(userDto.getEmail())
                .mobileNumber(userDto.getMobileNumber())
                .build());
        return "buyTicket";
    }

    @PostMapping(value = "/routes/{id}/buy-ticket")
    public String buyTicket(@PathVariable("id") UUID id,
                            @ModelAttribute("passenger") @Valid PassengerFormDto passengerFormDto,
                            BindingResult bindingResult, Model model) {

        RouteDto routeDto = routeCacheProvider.get().findById(id);
        model.addAttribute("route", routeDto);
        boolean hasPassenger = ticketService.hasPassenger(passengerFormDto);
        System.out.println(hasPassenger);
        if (hasPassenger) {
            bindingResult.rejectValue("validationMessage","You have already bought a ticket.");
        }
        if (ticketService.isTimeLeft(passengerFormDto)) {
            bindingResult.rejectValue("validationMessage","No time left");
        }
        if (ticketService.areTicketsAvailable(passengerFormDto)) {
            bindingResult.rejectValue("validationMessage", "No tickets left");
        }
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors().toString());
            return "redirect:/routes/{id}?fail";
        }
        TicketDto ticketDto = ticketService.buyTicket(passengerFormDto);
        System.out.println(ticketDto.toString());
        return "redirect:/routes/{id}?success";
    }

}
