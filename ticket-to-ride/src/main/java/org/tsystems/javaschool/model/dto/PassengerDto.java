package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto implements Serializable {

    private int id;

    private UserDto userDto;

    @NotBlank(message = "First name must not be blank")
    @Pattern(regexp = "^[a-zA-Z-]+$",message = "Latin letters and dashes are allowed")
    private String firstName;

    @NotBlank(message = "Second name must not be blank")
    @Pattern(regexp = "^[a-zA-Z-]+$",message = "Latin letters and dashes are allowed")
    private String secondName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birth date must be in past")
    private LocalDate birthDate;

    @NotBlank(message = "Mobile number can not be blank")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Enter mobile number according to pattern")
    private String mobileNumber;

    @Pattern(regexp = "^([A-Za-z0-9+_.-]+@(.+))?$", message = "Latin letters, digits, dashes, underscores and points are allowed")
    private String email;


}
