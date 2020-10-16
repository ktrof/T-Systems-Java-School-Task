package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.tsystems.javaschool.constraint.ValidateFields;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidateFields.List(
        value = {
                @ValidateFields(
                        first = "password",
                        second = "confirmPassword",
                        message = "Passwords must match"
                ),
                @ValidateFields(
                        first = "email",
                        second = "confirmEmail",
                        message = "Emails must match"
                )
        }
)
public class UpdateUserFormDto implements Serializable {

    private int id;

    @NotBlank(message = "Set login")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",message = "Latin letters, digits and dashes are allowed")
    private String login;

    @NotBlank(message = "Set password")
    private String password;

    @NotBlank(message = "Confirm password")
    private String confirmPassword;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",message = "Latin letters, digits and dashes are allowed")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",message = "Latin letters, digits and dashes are allowed")
    private String secondName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birth date must be in past")
    private LocalDate birthDate;

    @NotBlank(message = "Set email")
    @Pattern(regexp = "^([A-Za-z0-9+_.-]+@(.+))?$",
            message = "Latin letters, digits, dashes, underscores and points are allowed")
    private String email;

    @NotBlank(message = "Confirm email")
    @Pattern(regexp = "^([A-Za-z0-9+_.-]+@(.+))?$",
            message = "Latin letters, digits, dashes, underscores and points are allowed")
    private String confirmEmail;

    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Enter mobile number according to pattern")
    private String mobileNumber;

}
