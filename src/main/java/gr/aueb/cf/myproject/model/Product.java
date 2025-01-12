package gr.aueb.cf.myproject.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "products")
public class Product extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String uuid;

    private String name;

    private String description;

    private String category;
    private String brand;

    @Column(name = "is_available")
    private Boolean isAvailable;

    private int quantity;
    private Float price;
//    private String imageName;
//
//    @Lob
//    private byte[] imageData;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionProduct> transactionProducts;

    @PrePersist
    public  void initializedUUID () {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }

}
