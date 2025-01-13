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

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String ssn;

    private String email;

    private String phone;

    private String address;

    private String addressNumber;

    private String city;

    private String zip;

    private GenderType gender;

    private Role role;

    private Boolean isActive;

}
