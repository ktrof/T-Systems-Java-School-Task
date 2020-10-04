package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private int id;
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private LocalDate birthDate;
    private String email;
    private String mobileNumber;

}
