package org.tsystems.javaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.tsystems.javaschool.service.TrainService;

@Controller
public class TrainController {

    @Autowired
    private TrainService trainService;

    @GetMapping(value = "/trains")
    public String getAllTrains(Model model) {
        model.addAttribute("trains", trainService.getAll());
        return "trains";
    }
}
