package org.tsystems.javaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.service.TrainService;

@Controller
@RequestMapping("/sbb/trains")
public class TrainController {

//    @Autowired
//    private TrainService trainService;
//
//    @GetMapping
//    public String getAllTrains(Model model) {
//        model.addAttribute("trains", trainService.findAll());
//        return "trains";
//    }
//
//    @GetMapping(value = "?id={id}")
//    public TrainDto findById(@PathVariable int id, Model model) {
//        return trainService.findById(id);
//    }






}
