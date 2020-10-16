package org.tsystems.javaschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tsystems.javaschool.model.dto.RouteDto;
import org.tsystems.javaschool.model.dto.SearchResultDto;
import org.tsystems.javaschool.model.dto.SearchRouteFormDto;
import org.tsystems.javaschool.model.dto.StationDto;
import org.tsystems.javaschool.service.RouteService;
import org.tsystems.javaschool.service.StationService;

import javax.inject.Provider;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Controller
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final StationService stationService;
    private final Provider<RouteCache> routeCacheProvider;

    @ModelAttribute("stations")
    public List<StationDto> stationDtoList() {
        return stationService.getAll();
    }

    @GetMapping(value = "/")
    public String getSearchForm(Model model) {
        model.addAttribute("search", SearchRouteFormDto.builder().build());
        return "index";
    }

    @PostMapping(value = "/routes")
    public String getSearchResult(Model model, @ModelAttribute("searchForm") SearchRouteFormDto searchRouteFormDto) {
        if (searchRouteFormDto.getRideDate() == null) {
            searchRouteFormDto.setRideDate(LocalDate.now());
        }
        SearchResultDto searchResultDto = routeService.findRouteGroups(searchRouteFormDto);
        List<RouteDto> routeDtoList = searchResultDto.getDiscoveredRoutes();
        routeCacheProvider.get().addAll(routeDtoList);
        model.addAttribute("discoveredRoutes", routeDtoList);
        model.addAttribute("selectedRoute", RouteDto.builder().build());
        return "searchResult";
    }

    @PostMapping(value = "/")
    public String findRoutes(@ModelAttribute("search") @Valid SearchRouteFormDto searchRouteDto,
                             Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        model.addAttribute("searchForm", searchRouteDto);
        return "redirect:/routes";
    }

}
