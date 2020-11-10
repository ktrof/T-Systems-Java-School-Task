package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.tsystems.javaschool.model.dto.rideschedule.RideScheduleDto;
import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionDto;
import org.tsystems.javaschool.model.dto.section.SectionDto;
import org.tsystems.javaschool.model.dto.train.AddTrainFormDto;
import org.tsystems.javaschool.model.dto.train.DelayFormDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;
import org.tsystems.javaschool.model.dto.train.TrainType;
import org.tsystems.javaschool.service.RideScheduleService;
import org.tsystems.javaschool.service.ScheduleSectionService;
import org.tsystems.javaschool.service.SectionService;
import org.tsystems.javaschool.service.TrainService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;
    private final SectionService sectionService;
    private final ScheduleSectionService scheduleSectionService;
    private final RideScheduleService rideScheduleService;

    @ModelAttribute("train")
    public AddTrainFormDto trainFormDto() {
        return AddTrainFormDto.builder().build();
    }

    @ModelAttribute("delay")
    public DelayFormDto delayFormDto() {
        return DelayFormDto.builder().build();
    }

    @ModelAttribute("sections")
    public List<SectionDto> sectionDtoList() {
        return sectionService.getAll();
    }

    @GetMapping(value = "/admin/trains")
    public String getAllTrains(Model model) {
        model.addAttribute("trains", trainService.getAll());
        return "trains";
    }

    @GetMapping(value = "/admin/trains/{id}")
    public String getTrainByIdAdmin(@PathVariable("id") String id, Model model,
                                    @RequestParam("rideDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        TrainDto trainDto = trainService.getById(id);
        List<RideScheduleDto> rideScheduleDtoList = rideScheduleService.getByTrainAndRideDate(trainDto, date);
        List<ScheduleSectionDto> scheduleSectionDtoList = scheduleSectionService.getByTrainAndRideDate(trainDto, date);
        model.addAttribute("trainItem", trainDto);
        model.addAttribute("rideScheduleList", rideScheduleDtoList);
        model.addAttribute("scheduleSectionList", scheduleSectionDtoList);
        return "trainItemAdmin";
    }


    @GetMapping(value = "/admin/trains/add")
    public String getTrainForm() {
        return "addTrain";
    }

    @PostMapping(value = "/admin/trains/add")
    public String addTrain(@ModelAttribute("train") @Valid AddTrainFormDto trainFormDto,
                           BindingResult result, Model model) {
        TrainDto existingTrain = trainService.getById(trainFormDto.getId());
        if (existingTrain != null) {
            result.rejectValue("id", null, "There is already a train with that id");
        }
        if (result.hasErrors()) {
            return "addTrain";
        }
        model.addAttribute("trainTypes", TrainType.values());
        trainService.save(trainFormDto);
        return "redirect:/trains";
    }

    @PostMapping(value = "/admin/trains/{id}/cancel-train")
    public String cancelTrain(@PathVariable String id) {
        trainService.cancelTrain(id);
        return "redirect:/admin/trains/{id}?cancelled";
    }

    @PostMapping(value = "/admin/trains/{id}/cancel-ride")
    public String cancelRide(@PathVariable String id,
                             @RequestParam("rideDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        trainService.cancelRide(id, date);
        return "redirect:/admin/trains/{id}";
    }

    @PostMapping(value = "/admin/trains/{id}/restart-train")
    public String restartTrain(@PathVariable String id) {
        trainService.restartTrain(id);
        return "redirect:admin/trains/{id}?restarted";
    }

    @PostMapping(value = "/admin/trains/{id}/cancel-ride")
    public String restartRide(@PathVariable String id,
                             @RequestParam("rideDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        trainService.restartRide(id, date);
        return "redirect:/admin/trains/{id}";
    }

    @PostMapping(value = "/admin/trains/{id}/delay")
    public String delayTrain(@PathVariable String id,
                             @ModelAttribute("delay") @Valid DelayFormDto delayFormDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "trainItemAdmin";
        }
        trainService.delayTrain(id, delayFormDto);
        return "redirect:/admin/trains/{id}";
    }

    @GetMapping(value = "/trains/{id}")
    public String getTrainById(@PathVariable("id") String id, Model model,
                               @RequestParam("rideDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        TrainDto trainDto = trainService.getById(id);
        List<ScheduleSectionDto> scheduleSectionDtoList = scheduleSectionService.getByTrainAndRideDate(trainDto, date);
        model.addAttribute("trainItem", trainDto);
        model.addAttribute("scheduleSectionList", scheduleSectionDtoList);
        return "trainItem";
    }

}
