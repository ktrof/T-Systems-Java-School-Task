package org.tsystems.javaschool.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
public class SearchResultDto implements Serializable {

    private List<RouteDto> discoveredRoutes;

}
