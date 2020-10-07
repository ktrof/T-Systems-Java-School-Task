package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.AddStationFormDto;
import org.tsystems.javaschool.model.dto.SectionDto;
import org.tsystems.javaschool.model.dto.StationDto;
import org.tsystems.javaschool.service.StationService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Controller
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @ModelAttribute("station")
    public AddStationFormDto stationFormDto() {
        return AddStationFormDto.builder().build();
    }

    @ModelAttribute("stations")
    public List<StationDto> stationDtoList() {
        return stationService.getAll();
    }

    @GetMapping(value = "/stations")
    public String getAllStations() {
        return "stations";
    }

    @PostMapping(value = "/stations/add")
    public String addStation(@ModelAttribute("station") @Valid AddStationFormDto stationFormDto,
                             BindingResult bindingResult) {
        StationDto existingStation = stationService.getByName(stationFormDto.getName());
        if (existingStation != null) {
            bindingResult.rejectValue("name", null, "There is already a station with that name");
        }
        if (bindingResult.hasErrors()) {
            return "addStation";
        }
        stationService.save(stationFormDto);
        return "redirect:/stations";
    }

    @GetMapping(value = "/stations/add")
    public String getStationAddForm() {
        return "addStation";
    }

    @PostMapping(value = "/stations")
    public String editStation(@Valid @ModelAttribute StationDto stationDto, Model model) {
        stationService.edit(stationDto);
        List<StationDto> result = stationService.getAll();
        model.addAttribute("stations", result);
        return "stations";
    }
}
