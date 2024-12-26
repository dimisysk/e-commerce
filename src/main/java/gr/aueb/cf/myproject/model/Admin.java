package gr.aueb.cf.myproject.model;


import jakarta.persistence.*;
import lombok.*;



@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "admins")
public class Admin extends BaseRole {


}
