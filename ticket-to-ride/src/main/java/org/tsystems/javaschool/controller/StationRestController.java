package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.tsystems.javaschool.model.dto.station.AddStationFormDto;
import org.tsystems.javaschool.model.dto.station.DetailedStationDto;
import org.tsystems.javaschool.model.dto.station.StationDto;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.service.ScheduleSectionService;
import org.tsystems.javaschool.service.StationService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class StationRestController {

    private final StationService stationService;
    private final ScheduleSectionService scheduleSectionService;

    @GetMapping(value = "/stations")
    public List<StationDto> getAllStations() {
        return stationService.getAll();
    }

    @GetMapping(value = "/stations/{id}")
    public DetailedStationDto getStationById(@PathVariable int id) {
        StationDto stationDto = stationService.getById(id);
        return DetailedStationDto.builder()
                .stationDto(stationDto)
                .departureSectionDtoList(scheduleSectionService.getByDepartureStationAndRideDate(
                        stationDto,
                        LocalDate.now()
                ))
                .destinationSectionDtoList(scheduleSectionService.getByDestinationStationAndRideDate(
                        stationDto,
                        LocalDate.now()
                ))
                .build();
    }

    @PatchMapping(value = "/stations/{id}/close")
    public void closeStation(@PathVariable int id) {
        stationService.close(id);
    }

    @PatchMapping(value = "/stations/{id}/open")
    public void openStation(@PathVariable int id) {
        stationService.open(id);
    }

    @PostMapping(value = "/stations")
    public AddStationFormDto addStation(@Valid @RequestBody AddStationFormDto stationFormDto) {
        return stationService.save(stationFormDto);
    }

    @PutMapping(value = "/stations")
    public StationDto editStation(@Valid @RequestBody StationDto stationDto) {
        return stationService.edit(stationDto);
    }

}
