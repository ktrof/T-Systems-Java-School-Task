package org.tsystems.javaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.tsystems.javaschool.repository.UserRepository;

/**
 * @author Trofim Kremen
 */
@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

}
