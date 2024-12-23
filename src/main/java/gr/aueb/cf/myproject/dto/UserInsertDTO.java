package gr.aueb.cf.myproject.dto;

import gr.aueb.cf.myproject.core.enums.GenderType;
import gr.aueb.cf.myproject.core.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInsertDTO {

    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters.")
    private String username;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password;

    @NotEmpty(message = "Firstname is required")
    private String firstName;

    @NotEmpty(message = "Lastname is required")
    private String lastName;

    @NotNull(message = "ssn must not be null")
    private String ssn;

    @Email(message = "Not a valid email address")
    private String email;

    @NotNull(message = "phone must not be null")
    private String phone;

    @NotNull(message = "address must not be null")
    private String address;


    private String addressNumber;

    @NotNull(message = "city must not be null")
    private String city;

    @NotNull(message = "zip code must not be null")
    private String zip;

    @NotNull(message = "gender must not be null")
    private GenderType gender;

    @NotNull(message = "role must not be null")
    private Role role;

    @Builder.Default
    private Boolean isActive = true;
}
