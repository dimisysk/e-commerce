package gr.aueb.cf.myproject.service;

import gr.aueb.cf.myproject.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.myproject.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.myproject.dto.AdminInsertDTO;
import gr.aueb.cf.myproject.dto.AdminReadOnlyDTO;
import gr.aueb.cf.myproject.mapper.AdminMapper;
import gr.aueb.cf.myproject.mapper.CustomerMapper;
import gr.aueb.cf.myproject.mapper.EntityToDtoMapper;
import gr.aueb.cf.myproject.model.Admin;
import gr.aueb.cf.myproject.model.Customer;
import gr.aueb.cf.myproject.repository.AdminRepository;
import gr.aueb.cf.myproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService {

    private AdminRepository adminRepository;
    private CustomerMapper customerMapper;
    private AdminMapper adminMapper;
    private UserRepository userRepository;
    private EntityToDtoMapper entityToDtoMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, CustomerMapper customerMapper, UserRepository userRepository, EntityToDtoMapper entityToDtoMapper, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.customerMapper = customerMapper;
        this.userRepository = userRepository;
        this.entityToDtoMapper = entityToDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AdminReadOnlyDTO saveAdmin(AdminInsertDTO adminInsertDTO) throws AppObjectAlreadyExists {
        if (userRepository.findByUsername(adminInsertDTO.getUser().getUsername()).isPresent()) {
            throw new AppObjectAlreadyExists("Admin", "User with Username:" + adminInsertDTO.getUser().getUsername() + "already exists");
        }


        Admin admin = adminMapper.mapToAdminEntity(adminInsertDTO);
        admin.getUser().setPassword(passwordEncoder.encode(admin.getUser().getPassword()));
        Admin savedAdmin = adminRepository.save(admin);
        return entityToDtoMapper.mapToAdminReadOnlyDTO(savedAdmin);
    }

    @Transactional
    public AdminReadOnlyDTO updateAdmin(Long Id, AdminInsertDTO adminInsertDTO) throws AppObjectNotFoundException {
        Admin admin = adminRepository.findById(Id)
                .orElseThrow(() -> new AppObjectNotFoundException("Admin", "Admin not found with ID: " + Id));

        admin.getUser().setFirstName(adminInsertDTO.getUser().getFirstName());
        admin.getUser().setLastName(adminInsertDTO.getUser().getLastName());
        // Update other fields...

        Admin updatedAdmin = adminRepository.save(admin);
        return entityToDtoMapper.mapToAdminReadOnlyDTO(updatedAdmin);
    }

    @Transactional
    public void deleteAdmin(Long adminId) throws AppObjectNotFoundException {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AppObjectNotFoundException("Admin", "Admin not found with ID: " + adminId));
        adminRepository.delete(admin);
    }

    public AdminReadOnlyDTO getAdminById(Long adminId) throws AppObjectNotFoundException {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AppObjectNotFoundException("Admin", "Admin not found with ID: " + adminId));
        return entityToDtoMapper.mapToAdminReadOnlyDTO(admin);
    }

    public List<AdminReadOnlyDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(entityToDtoMapper::mapToAdminReadOnlyDTO)
                .toList();
    }
}
