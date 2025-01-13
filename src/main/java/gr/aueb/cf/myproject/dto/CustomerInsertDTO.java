package gr.aueb.cf.myproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerInsertDTO {


    private UserInsertDTO user;
    private Long id;

    @NotNull(message = "User discount's Card Number must not be null")
    private String discountCardNumber;
}
