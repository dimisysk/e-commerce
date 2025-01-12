package gr.aueb.cf.myproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReadOnlyDTO {
    private Long id;
//    private String uuid; // Αν χρησιμοποιείτε UUIDs
    private String name;
    private String description;
    private String category;
    private String brand;
    private Boolean isAvailable;
    private int quantity;
    private Float price;
//    private String imageName;
//    private byte[] imageData;
}
