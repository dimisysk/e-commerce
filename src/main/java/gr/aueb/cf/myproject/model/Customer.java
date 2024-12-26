package gr.aueb.cf.myproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "customers")
public class Customer extends BaseRole {

    private Integer discountCardNumber;

}
