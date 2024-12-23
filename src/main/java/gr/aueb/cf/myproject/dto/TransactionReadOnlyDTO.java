package gr.aueb.cf.myproject.dto;

import gr.aueb.cf.myproject.core.enums.Status;
import gr.aueb.cf.myproject.model.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionReadOnlyDTO {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;  // Ημερομηνία τελευταίας ενημέρωσης
    private Status status;           // Κατάσταση της συναλλαγής
    private Long userId;               // Πλήρες αντικείμενο User αν χρειάζεται
}
