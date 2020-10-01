package org.tsystems.javaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.service.TrainService;

import java.util.List;

@Controller
public class TrainController {

    @Autowired
    private TrainService trainService;

    @GetMapping(value = "/trains")
    public ModelAndView getAllTrains() {
        ModelAndView modelAndView = new ModelAndView();
        List<TrainDto> result = trainService.getAll();
        modelAndView.setViewName("trains");
        modelAndView.addObject("trains", result);
        return modelAndView;
    }

}
