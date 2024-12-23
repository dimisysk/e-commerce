package gr.aueb.cf.myproject.model;

import gr.aueb.cf.myproject.dto.UserInsertDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "admins")
public class Admin extends BaseRole {
    private User user;
}
