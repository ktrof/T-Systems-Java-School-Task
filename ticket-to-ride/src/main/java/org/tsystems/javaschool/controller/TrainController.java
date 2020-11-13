package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tsystems.javaschool.model.dto.ride.RideDto;
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
import org.tsystems.javaschool.service.impl.RideService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;
    private final SectionService sectionService;
    private final ScheduleSectionService scheduleSectionService;
    private final RideScheduleService rideScheduleService;
    private final RideService rideService;

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
        return "trainsAdmin";
    }

    @GetMapping(value = "/admin/trains/{id}", params = "rideDate")
    public String getTrainByIdAdmin(@PathVariable("id") String id, Model model,
                                    @RequestParam(name = "rideDate")
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        TrainDto trainDto = trainService.getById(id);
        RideDto rideDto = rideService.getByTrainAndRideDate(trainDto, date);
        List<RideScheduleDto> rideScheduleDtoList = rideScheduleService.getByTrainAndRideDate(trainDto, date);
        List<ScheduleSectionDto> scheduleSectionDtoList = scheduleSectionService.getByTrainAndRideDate(trainDto, date);
        model.addAttribute("trainItem", trainDto);
        model.addAttribute("rideItem", rideDto);
        model.addAttribute("rideScheduleList", rideScheduleDtoList);
        model.addAttribute("scheduleSectionList", scheduleSectionDtoList);
        return "trainItemAdmin";
    }


    @GetMapping(value = "/admin/trains/add")
    public String getTrainForm(Model model) {
        model.addAttribute("trainTypes", TrainType.values());
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
        return "redirect:/admin/trains";
    }

    @PostMapping(value = "/admin/trains/{id}/cancel-train", params = "rideDate")
    public String cancelTrain(@PathVariable String id,
                              @RequestParam(name = "rideDate")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("rideDate", date.format(DateTimeFormatter.ISO_DATE));
        trainService.cancelTrain(id);
        return "redirect:/admin/trains/{id}";
    }

    @PostMapping(value = "/admin/trains/{id}/cancel-ride")
    public String cancelRide(@PathVariable String id,
                             @RequestParam(name = "rideDate")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("rideDate", date.format(DateTimeFormatter.ISO_DATE));
        trainService.cancelRide(id, date);
        return "redirect:/admin/trains/{id}";
    }

    @PostMapping(value = "/admin/trains/{id}/restart-train")
    public String restartTrain(@PathVariable String id,
                               @RequestParam(name = "rideDate")
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("rideDate", date.format(DateTimeFormatter.ISO_DATE));
        trainService.restartTrain(id);
        return "redirect:/admin/trains/{id}";
    }

    @PostMapping(value = "/admin/trains/{id}/restart-ride")
    public String restartRide(@PathVariable String id,
                              @RequestParam(name = "rideDate")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("rideDate", date.format(DateTimeFormatter.ISO_DATE));
        trainService.restartRide(id, date);
        return "redirect:/admin/trains/{id}";
    }

    @PostMapping(value = "/admin/trains/{id}/delay")
    public String delayTrain(@PathVariable String id,
                             @ModelAttribute("delay")
                             @Valid DelayFormDto delayFormDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("rideDate", LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/trains/{id}?delayError";
        }
        trainService.delayTrain(id, delayFormDto);
        return "redirect:/admin/trains/{id}";
    }

    @GetMapping(value = "/trains/{id}", params = "rideDate")
    public String getTrainById(@PathVariable("id") String id, Model model,
                               @RequestParam(name = "rideDate")
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        TrainDto trainDto = trainService.getById(id);
        List<ScheduleSectionDto> scheduleSectionDtoList = scheduleSectionService.getByTrainAndRideDate(trainDto, date);
        model.addAttribute("trainItem", trainDto);
        model.addAttribute("scheduleSectionList", scheduleSectionDtoList);
        return "trainItem";
    }

}
