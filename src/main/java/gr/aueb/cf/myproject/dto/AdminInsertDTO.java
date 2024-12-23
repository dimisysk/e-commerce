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
public class AdminInsertDTO {

    @NotNull(message = "User details must not be null")
    private UserInsertDTO user;
}
