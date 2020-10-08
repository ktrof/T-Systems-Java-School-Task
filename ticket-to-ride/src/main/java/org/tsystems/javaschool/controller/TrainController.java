package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.AddTrainFormDto;
import org.tsystems.javaschool.model.dto.SectionDto;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.service.SectionService;
import org.tsystems.javaschool.service.TrainService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;
    private final SectionService sectionService;

    @ModelAttribute("train")
    public AddTrainFormDto trainFormDto() {
        return AddTrainFormDto.builder().build();
    }

    @ModelAttribute("sections")
    public List<SectionDto> sectionDtoList() {
        return sectionService.getAll();
    }

    @GetMapping(value = "/trains")
    public String getAllTrains(Model model) {
        model.addAttribute("trains", trainService.getAll());
        return "trains";
    }

    @GetMapping(value = "/trains/add")
    public String getTrainForm() {
        return "addTrain";
    }

    @PostMapping(value = "/trains/add")
    public String addTrain(@ModelAttribute("train") @Valid AddTrainFormDto trainFormDto,
                           BindingResult result) {
        TrainDto existingTrain = trainService.getById(trainFormDto.getId());
        if (existingTrain != null) {
            result.rejectValue("id", null, "There is already a train with that id");
        }
        if (result.hasErrors()) {
            return "addTrain";
        }
        System.out.println(trainFormDto.getDates());
        trainService.save(trainFormDto);
        return "trains";
    }

}
