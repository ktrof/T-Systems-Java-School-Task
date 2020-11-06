package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.station.AddStationFormDto;
import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionDto;
import org.tsystems.javaschool.model.dto.station.StationDto;
import org.tsystems.javaschool.service.ScheduleSectionService;
import org.tsystems.javaschool.service.StationService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Controller
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;
    private final ScheduleSectionService scheduleSectionService;

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

    @GetMapping(value = "/stations/{id}")
    public String getStationById(@PathVariable("id") int id, Model model) {
        StationDto stationDto = stationService.getById(id);
        List<ScheduleSectionDto> departureSectionDtoList = scheduleSectionService
                .getByDepartureStationAndRideDate(stationDto, LocalDate.now());

        List<ScheduleSectionDto> destinationSectionDtoList = scheduleSectionService
                .getByDestinationStationAndRideDate(stationDto, LocalDate.now());

        model.addAttribute("stationItem", stationDto);
        model.addAttribute("departureSectionDtoList", departureSectionDtoList);
        model.addAttribute("destinationSectionDtoList", destinationSectionDtoList);
        return "stationItem";
    }

    @PostMapping(value = "/stations")
    public String editStation(@Valid @ModelAttribute StationDto stationDto, Model model) {
        stationService.edit(stationDto);
        List<StationDto> result = stationService.getAll();
        model.addAttribute("stations", result);
        return "stations";
    }
}
