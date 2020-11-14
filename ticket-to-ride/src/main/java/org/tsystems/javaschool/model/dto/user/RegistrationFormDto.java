package org.tsystems.javaschool.model.dto.user;

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
public class RegistrationFormDto implements Serializable {

    private int id;

    @NotBlank(message = "Set login")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",message = "Latin letters, digits and dashes are allowed")
    private String login;

    @NotBlank(message = "Set email")
    @Pattern(regexp = "^([A-Za-z0-9+_.-]+@(.+))?$",
            message = "Latin letters, digits, dashes, underscores and points are allowed")
    private String email;

    @NotBlank(message = "Confirm email")
    @Pattern(regexp = "^([A-Za-z0-9+_.-]+@(.+))?$",
            message = "Latin letters, digits, dashes, underscores and points are allowed")
    private String confirmEmail;

    @NotBlank(message = "Set password")
    private String password;

    @NotBlank(message = "Confirm password")
    private String confirmPassword;

}
