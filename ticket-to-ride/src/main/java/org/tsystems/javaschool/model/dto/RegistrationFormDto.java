package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tsystems.javaschool.constraint.ValidateFields;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidateFields(
        first = "password",
        second = "confirmPassword",
        message = "Passwords must match"
)
public class RegistrationFormDto implements Serializable {

    private int id;

    @NotBlank(message = "Set login")
    @Pattern(regexp = "^[a-zA-Z0-9-]+$",message = "Latin letters, digits and dashes are allowed")
    private String login;

    @NotBlank(message = "Set password")
    private String password;

    @NotBlank(message = "Confirm password")
    private String confirmPassword;

}
