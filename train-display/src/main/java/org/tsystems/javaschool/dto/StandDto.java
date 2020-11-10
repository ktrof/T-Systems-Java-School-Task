package org.tsystems.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandDto implements Serializable {

    private String stationName;
    private boolean isClosed;
    private String rideDate;
    private List<StandRowDto> standRowDtoList;
}
