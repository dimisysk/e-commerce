package gr.aueb.cf.myproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionInsertDTO {
    private Float amount;
    private Long userId;
}
