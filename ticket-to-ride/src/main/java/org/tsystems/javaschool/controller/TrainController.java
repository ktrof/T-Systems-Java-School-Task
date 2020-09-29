package org.tsystems.javaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.repository.TrainRepository;
import org.tsystems.javaschool.service.TrainService;

import java.util.List;

@Controller
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainRepository trainRepository;

    @GetMapping(value = "/trains")
    public ModelAndView getAllTrains() {
        ModelAndView modelAndView = new ModelAndView();
        List<TrainDto> trains = trainService.getAll();
        modelAndView.addObject("trains", trains);
        modelAndView.setViewName("trainsPage");
        return modelAndView;
    }

    @GetMapping(value = "/trains/{id}")
    public TrainDto findById(@PathVariable String id, Model model) {
        return trainService.getById(id);
    }

}
