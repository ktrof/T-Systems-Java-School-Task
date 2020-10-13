package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.PassengerDto;
import org.tsystems.javaschool.model.dto.RouteDto;
import org.tsystems.javaschool.service.TicketService;

import javax.inject.Provider;
import javax.validation.Valid;
import java.util.UUID;

/**
 * @author Trofim Kremen
 */
@Controller
@RequiredArgsConstructor
public class TicketController {

    private final Provider<RouteCache> routeCacheProvider;
    private final TicketService ticketService;

    @GetMapping(value = "/routes/{id}")
    public String getPassengerForm(@PathVariable("id") UUID id, Model model) {
        RouteDto routeDto = routeCacheProvider.get().findById(id);
        model.addAttribute("route", routeDto);
        model.addAttribute("passenger", PassengerDto.builder().build());
        return "buyTicket";
    }

    @PostMapping(value = "/buy-ticket")
    public String buyTicket(@ModelAttribute("passenger") @Valid PassengerDto passengerDto,
                            @ModelAttribute("route") RouteDto routeDto, BindingResult bindingResult) {

        return "redirect:/routes";
    }

}
