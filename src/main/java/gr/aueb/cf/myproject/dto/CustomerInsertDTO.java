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

    @NotNull(message = "User details must not be null")
    private UserInsertDTO user;

    private Integer discountCardNumber;
}
