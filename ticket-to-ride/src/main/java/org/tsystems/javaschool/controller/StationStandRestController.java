package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.tsystems.javaschool.model.dto.StandDto;
import org.tsystems.javaschool.service.StationStandService;

import java.time.LocalDate;

/**
 * @author Trofim Kremen
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class StationStandRestController {

    private final StationStandService stationStandService;

    @GetMapping(value = "/stations/{id}/stand")
    public StandDto getStationDepartureStand(
            @PathVariable int id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return stationStandService.getByStationIdAndRideDate(id, date);
    }

}
