package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.service.TrainService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @GetMapping(value = "/trains")
    public ModelAndView getAllTrains() {
        ModelAndView modelAndView = new ModelAndView();
        List<TrainDto> result = trainService.getAll();
        modelAndView.setViewName("trains");
        modelAndView.addObject("trains", result);
        return modelAndView;
    }

}
