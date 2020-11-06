package org.tsystems.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationDto implements Serializable {

    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String timezone;
    private boolean closed;

}
