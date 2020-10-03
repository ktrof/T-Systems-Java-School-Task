package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.StationDto;
import org.tsystems.javaschool.service.StationService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Controller
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping(value = "/stations")
    public String getAllStations(Model model) {
        List<StationDto> result = stationService.getAll();
        model.addAttribute("stations", result);
        return "stations";
    }


    @PostMapping(value = "/stations")
    public String editStation(@Valid @ModelAttribute StationDto stationDto, Model model) {
        stationService.edit(stationDto);
        List<StationDto> result = stationService.getAll();
        model.addAttribute("stations", result);
        return "stations";
    }
}
