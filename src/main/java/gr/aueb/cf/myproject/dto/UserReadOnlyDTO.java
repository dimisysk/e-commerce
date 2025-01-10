package gr.aueb.cf.myproject.dto;


import gr.aueb.cf.myproject.core.enums.GenderType;
import gr.aueb.cf.myproject.core.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReadOnlyDTO {

    @NotNull(message = "Id must not be null")
    private Long id;

    @NotNull(message = "firstname must not be null")
    private String firstName;

    @NotNull(message = "lastname must not be null")
    private String lastName;

    @NotNull(message = "ssn must not be null")
    private String ssn;

    @NotNull(message = "email must not be null")
    private String email;

    @NotNull(message = "phone must not be null")
    private String phone;

    @NotNull(message = "address must not be null")
    private String address;

    @NotNull(message = "address number must not be null")
    private String addressNumber;

    @NotNull(message = "city must not be null")
    private String city;

    @NotNull(message = "zip code must not be null")
    private String zip;

    @NotNull(message = "gender must not be null")
    private GenderType gender;

    @NotNull(message = "role must not be null")
    private Role role;


//    @NotNull(message = "this field must not be null")
//    private Boolean isActive;

}
