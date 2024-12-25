package gr.aueb.cf.myproject.service;

import gr.aueb.cf.myproject.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.myproject.dto.AdminInsertDTO;
import gr.aueb.cf.myproject.dto.AdminReadOnlyDTO;
import gr.aueb.cf.myproject.mapper.CustomerAdminMapper;
import gr.aueb.cf.myproject.mapper.EntityToDtoMapper;
import gr.aueb.cf.myproject.model.Admin;
import gr.aueb.cf.myproject.repository.AdminRepository;
import gr.aueb.cf.myproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminService {

    private AdminRepository adminRepository;
    private CustomerAdminMapper customerAdminMapper;
    private UserRepository userRepository;
    private EntityToDtoMapper entityToDtoMapper;

    @Autowired
    public AdminService(AdminRepository adminRepository, CustomerAdminMapper customerAdminMapper, UserRepository userRepository, EntityToDtoMapper entityToDtoMapper) {
        this.adminRepository = adminRepository;
        this.customerAdminMapper = customerAdminMapper;
        this.userRepository = userRepository;
        this.entityToDtoMapper = entityToDtoMapper;
    }

    @Transactional
    public AdminReadOnlyDTO saveAdmin(AdminInsertDTO adminInsertDTO) throws AppObjectAlreadyExists {
        if (userRepository.findByUsername(adminInsertDTO.getUser().getUsername()).isPresent()) {
            throw new AppObjectAlreadyExists("Admin", "User with Username:" + adminInsertDTO.getUser().getUsername() + "already exists");
        }

        Admin admin = customerAdminMapper.mapToAdminEntity(adminInsertDTO);
        Admin savedAdmin = adminRepository.save(admin);
        return entityToDtoMapper.mapToAdminReadOnlyDTO(savedAdmin);
    }
}
