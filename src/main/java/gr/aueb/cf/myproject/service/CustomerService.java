package gr.aueb.cf.myproject.service;


import gr.aueb.cf.myproject.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.myproject.dto.CustomerInsertDTO;
import gr.aueb.cf.myproject.dto.CustomerReadOnlyDTO;
import gr.aueb.cf.myproject.mapper.CustomerAdminMapper;
import gr.aueb.cf.myproject.mapper.EntityToDtoMapper;
import gr.aueb.cf.myproject.model.Customer;
import gr.aueb.cf.myproject.repository.AdminRepository;
import gr.aueb.cf.myproject.repository.CustomerRepository;
import gr.aueb.cf.myproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {


    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private CustomerAdminMapper customerAdminMapper;
    private EntityToDtoMapper entityToDtoMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           UserRepository userRepository,
                           CustomerAdminMapper customerAdminMapper,
                           EntityToDtoMapper entityToDtoMapper,
                           AdminRepository adminRepository)
    {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.customerAdminMapper = customerAdminMapper;
        this.entityToDtoMapper = entityToDtoMapper;

    }

    @Transactional(rollbackOn = Exception.class)
    public CustomerReadOnlyDTO saveCustomer(CustomerInsertDTO customerInsertDTO) throws AppObjectAlreadyExists {

        if (userRepository.findByUsername(customerInsertDTO.getUser().getUsername()).isPresent()) {
            throw new AppObjectAlreadyExists("Customer", "User with Username:" + customerInsertDTO.getUser().getUsername() + "already exists");
        }
        if (customerRepository.findByDiscountCardNumber(customerInsertDTO.getDiscountCardNumber()).isPresent()) {
            throw new AppObjectAlreadyExists("DiscountCardNumber", "Discount Card with the Number" +customerInsertDTO.getDiscountCardNumber() + "already exists");
        }

        Customer customer = customerAdminMapper.mapToCustomerEntity(customerInsertDTO);

        Customer savedCustomer = customerRepository.save(customer);

        return entityToDtoMapper.mapToCustomerReadOnlyDTO(savedCustomer);
    }

    public Page<CustomerReadOnlyDTO> getPaginatedCustomers(int page, int size, String sortBy) {
      String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return customerRepository.findAll(pageable).map(entityToDtoMapper::mapToCustomerReadOnlyDTO);
    }

    public Page<CustomerReadOnlyDTO> getSortedCustomers(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page,size, sort);
        return customerRepository.findAll(pageable).map(entityToDtoMapper::mapToCustomerReadOnlyDTO);
    }






}
