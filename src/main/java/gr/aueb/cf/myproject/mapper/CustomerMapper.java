package gr.aueb.cf.myproject.mapper;

import gr.aueb.cf.myproject.core.enums.Role;
import gr.aueb.cf.myproject.dto.AdminInsertDTO;
import gr.aueb.cf.myproject.dto.CustomerInsertDTO;
import gr.aueb.cf.myproject.dto.UserInsertDTO;
import gr.aueb.cf.myproject.model.Admin;
import gr.aueb.cf.myproject.model.Customer;
import gr.aueb.cf.myproject.model.User;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class CustomerMapper {



    private User mapToUserEntity(UserInsertDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setSsn(dto.getSsn());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setAddressNumber(dto.getAddressNumber());
        user.setCity(dto.getCity());
        user.setZip(dto.getZip());
        user.setGender(dto.getGender());
        return user;
    }


    public Customer mapToCustomerEntity(CustomerInsertDTO customerInsertDTO) {
        Customer customer = new Customer();
        customer.setDiscountCardNumber(customerInsertDTO.getDiscountCardNumber());

        User user = mapToUserEntity(customerInsertDTO.getUser());
        user.setRole(Role.CUSTOMER);
        user.setIsActive(true);
        customer.setUser(user);

        return customer;
    }





}