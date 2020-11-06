package org.tsystems.javaschool.model.dto.station;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionDto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedStationDto implements Serializable {

    private StationDto stationDto;
    private List<ScheduleSectionDto> departureSectionDtoList;
    private List<ScheduleSectionDto> destinationSectionDtoList;

}
