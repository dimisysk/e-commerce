package gr.aueb.cf.myproject.dto;


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

    @NotNull(message = "this field must not be null")
    private Boolean isActive;

}
