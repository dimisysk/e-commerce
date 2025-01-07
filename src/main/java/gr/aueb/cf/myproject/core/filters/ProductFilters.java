package gr.aueb.cf.myproject.core.filters;

import gr.aueb.cf.myproject.core.enums.GenderType;
import lombok.*;
import org.springframework.lang.Nullable;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class ProductFilters extends GenericFilters {

        @Nullable
        private String brand;

        @Nullable
        private String category;

        @Nullable
        private Float minPrice;

        @Nullable
        private Float maxPrice;

        @Nullable
        private Boolean isAvailable;

    }

