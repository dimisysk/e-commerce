package gr.aueb.cf.myproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInsertDTO {
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
