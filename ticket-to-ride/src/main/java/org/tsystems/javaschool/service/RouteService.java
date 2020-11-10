package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.SearchResultDto;
import org.tsystems.javaschool.model.dto.SearchRouteFormDto;

/**
 * The interface Route service.
 *
 * @author Trofim Kremen
 */
public interface RouteService {

    /**
     * Find route groups search result dto.
     *
     * @param searchRouteFormDto the search route form dto
     * @return the search result dto
     */
    SearchResultDto findRouteGroups(SearchRouteFormDto searchRouteFormDto);

}
