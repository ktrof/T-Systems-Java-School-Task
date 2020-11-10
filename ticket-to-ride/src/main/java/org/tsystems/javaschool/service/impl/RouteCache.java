package org.tsystems.javaschool.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.tsystems.javaschool.model.dto.route.RouteDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Trofim Kremen
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class RouteCache {

    private final Map<UUID, RouteDto> routeDtoMap = new HashMap<>();

    public RouteDto findById(UUID id) {
        return routeDtoMap.get(id);
    }

    public void addAll(Collection<RouteDto> routeDtoCollection) {
        for (RouteDto routeDto : routeDtoCollection) {
            routeDtoMap.put(routeDto.getId(), routeDto);
        }
    }
}
