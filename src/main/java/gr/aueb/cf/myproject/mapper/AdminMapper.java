package gr.aueb.cf.myproject.mapper;

import gr.aueb.cf.myproject.dto.AdminInsertDTO;
import gr.aueb.cf.myproject.dto.UserInsertDTO;
import gr.aueb.cf.myproject.model.Admin;
import gr.aueb.cf.myproject.model.User;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

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
        user.setIsActive(dto.getIsActive());
        user.setRole(dto.getRole());
        user.setZip(dto.getZip());
        user.setGender(dto.getGender());
        return user;
    }

    public Admin mapToAdminEntity(AdminInsertDTO adminInsertDTO) {
        Admin admin = new Admin();

        // Μετατροπή του user
        User user = mapToUserEntity(adminInsertDTO.getUser());
        admin.setUser(user);

        return admin;
    }
}
