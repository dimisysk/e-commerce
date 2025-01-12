package gr.aueb.cf.myproject.core.filters;


import gr.aueb.cf.myproject.core.enums.GenderType;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class CustomerFilters extends GenericFilters{
//    @Nullable
//    private String uuid;

    @Nullable
    private String userSsn;

    @Nullable
    private String lastName;

    @Nullable
    private GenderType userGender;

    @Nullable
    private Boolean active;

}
