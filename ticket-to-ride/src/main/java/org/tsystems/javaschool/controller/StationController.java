package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/admin/stations")
    public String getAllStations() {
        return "stationsAdmin";
    }

    @PostMapping(value = "/admin/stations/add")
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
        return "stationsAdmin";
    }

    @GetMapping(value = "admin/stations/add")
    public String getStationAddForm() {
        return "addStation";
    }

    @GetMapping(value = "/stations/{id}", params = "rideDate")
    public String getStationById(@PathVariable("id") int id, Model model,
                                 @RequestParam(name = "rideDate")
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        StationDto stationDto = stationService.getById(id);
        System.out.println(date.toString());
        List<ScheduleSectionDto> departureSectionDtoList = scheduleSectionService
                .getByDepartureStationAndRideDate(stationDto, date);

        List<ScheduleSectionDto> destinationSectionDtoList = scheduleSectionService
                .getByDestinationStationAndRideDate(stationDto, date);

        model.addAttribute("stationItem", stationDto);
        model.addAttribute("departureSectionDtoList", departureSectionDtoList);
        model.addAttribute("destinationSectionDtoList", destinationSectionDtoList);
        return "stationItem";
    }

    @GetMapping(value = "admin/stations/{id}", params = "rideDate")
    public String getStationByIdAdmin(@PathVariable("id") int id, Model model,
                                      @RequestParam(name = "rideDate")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        StationDto stationDto = stationService.getById(id);
        System.out.println(date.toString());
        List<ScheduleSectionDto> departureSectionDtoList = scheduleSectionService
                .getByDepartureStationAndRideDate(stationDto, date);

        List<ScheduleSectionDto> destinationSectionDtoList = scheduleSectionService
                .getByDestinationStationAndRideDate(stationDto, date);

        model.addAttribute("stationItem", stationDto);
        model.addAttribute("departureSectionDtoList", departureSectionDtoList);
        model.addAttribute("destinationSectionDtoList", destinationSectionDtoList);
        return "stationItemAdmin";
    }

    @PostMapping(value = "/admin/stations")
    public String editStation(@Valid @ModelAttribute StationDto stationDto, Model model) {
        stationService.edit(stationDto);
        List<StationDto> result = stationService.getAll();
        model.addAttribute("stations", result);
        return "stationsAdmin";
    }

    @PostMapping(value = "/admin/stations/{id}/close")
    public String closeStation(@PathVariable int id) {
        stationService.close(id);
        return "redirect:/admin/stations/{id}?closed";
    }

    @PostMapping(value = "/admin/stations/{id}/open")
    public String openStation(@PathVariable int id) {
        stationService.open(id);
        return "redirect:/admin/stations/{id}?opened";
    }
}
