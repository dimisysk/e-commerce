package gr.aueb.cf.myproject.mapper;

import gr.aueb.cf.myproject.dto.CustomerReadOnlyDTO;
import gr.aueb.cf.myproject.dto.UserReadOnlyDTO;
import gr.aueb.cf.myproject.dto.AdminReadOnlyDTO;
import gr.aueb.cf.myproject.model.Customer;
import gr.aueb.cf.myproject.model.User;
import gr.aueb.cf.myproject.model.Admin;
import org.springframework.stereotype.Component;

@Component
public class EntityToDtoMapper {


    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {

//        UserReadOnlyDTO userDTO = new UserReadOnlyDTO();
//        userDTO.setId(user.getId());
//        userDTO.setFirstName(user.getFirstName());
//        userDTO.setLastName(user.getLastName());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setSsn(user.getSsn());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setPhone(user.getPhone());

        return UserReadOnlyDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .ssn(user.getSsn())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .addressNumber(user.getAddressNumber())
                .city(user.getCity())
                .zip(user.getZip())
                .gender(user.getGender())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .build();
    }


    public  CustomerReadOnlyDTO mapToCustomerReadOnlyDTO(Customer customer) {
        UserReadOnlyDTO userDTO = mapToUserReadOnlyDTO(customer.getUser());
        return CustomerReadOnlyDTO.builder()
                .user(userDTO)
                .discountCardNumber(customer.getDiscountCardNumber())
                .build();
    }

    public AdminReadOnlyDTO mapToAdminReadOnlyDTO(Admin admin) {
        UserReadOnlyDTO userDTO = mapToUserReadOnlyDTO(admin.getUser());
        return AdminReadOnlyDTO.builder()
                .user(userDTO)
                .build();
    }
}

