package org.tsystems.javaschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author Trofim Kremen
 */
@ControllerAdvice(basePackages = "org.tsystems.javaschool.controller")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handlePageNotFoundError(NoHandlerFoundException e) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", e);
        model.addObject("errorMessage", "Error 404: Requested page not found.");
        return model;
    }


    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleOtherException(Exception exception) {
        ModelAndView model = new ModelAndView("error");
        log.error("Unexpected server error", exception);
        model.addObject("exception", exception);
        model.addObject("errorMessage", "Sorry! An unexpected technical error occurred. Please try again later.");
        return model;
    }

}
