package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto implements Serializable {


    private Integer id;
    private String firstName;
    private String secondName;
    private LocalDate birthDate;
    private String mobileNumber;
    private String email;

}
