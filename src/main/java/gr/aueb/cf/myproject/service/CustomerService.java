package gr.aueb.cf.myproject.service;


import gr.aueb.cf.myproject.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.myproject.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.myproject.core.filters.CustomerFilters;
import gr.aueb.cf.myproject.core.filters.Paginated;
import gr.aueb.cf.myproject.core.specifications.CustomerSpecification;
import gr.aueb.cf.myproject.dto.CustomerInsertDTO;
import gr.aueb.cf.myproject.dto.CustomerReadOnlyDTO;
import gr.aueb.cf.myproject.dto.UserReadOnlyDTO;
import gr.aueb.cf.myproject.mapper.CustomerMapper;
import gr.aueb.cf.myproject.mapper.EntityToDtoMapper;
import gr.aueb.cf.myproject.model.Customer;
import gr.aueb.cf.myproject.model.User;
import gr.aueb.cf.myproject.repository.AdminRepository;
import gr.aueb.cf.myproject.repository.CustomerRepository;
import gr.aueb.cf.myproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {


    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private CustomerMapper customerMapper;
    private EntityToDtoMapper entityToDtoMapper;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           UserRepository userRepository,
                           CustomerMapper customerMapper,
                           EntityToDtoMapper entityToDtoMapper,
                           PasswordEncoder passwordEncoder
                           )
    {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.customerMapper = customerMapper;
        this.entityToDtoMapper = entityToDtoMapper;
        this.passwordEncoder = passwordEncoder;

    }

    @Transactional(rollbackOn = Exception.class)
    public CustomerReadOnlyDTO saveCustomer(CustomerInsertDTO customerInsertDTO) throws AppObjectAlreadyExists {

        if (userRepository.findByUsername(customerInsertDTO.getUser().getUsername()).isPresent()) {
            throw new AppObjectAlreadyExists("Customer", "User with Username:" + customerInsertDTO.getUser().getUsername() + "already exists");
        }
        if (customerRepository.findByDiscountCardNumber(customerInsertDTO.getDiscountCardNumber()).isPresent()) {
            throw new AppObjectAlreadyExists("DiscountCardNumber", "Discount Card with the Number" +customerInsertDTO.getDiscountCardNumber() + "already exists");
        }

        Customer customer = customerMapper.mapToCustomerEntity(customerInsertDTO);
        customer.getUser().setPassword(passwordEncoder.encode(customer.getUser().getPassword()));
        Customer savedCustomer = customerRepository.save(customer);

        return entityToDtoMapper.mapToCustomerReadOnlyDTO(savedCustomer);
    }

    public Page<CustomerReadOnlyDTO> getPaginatedCustomers(int page, int size) {
      String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return customerRepository.findAll(pageable).map(entityToDtoMapper::mapToCustomerReadOnlyDTO);
    }

    @Transactional
    public Paginated<CustomerReadOnlyDTO> getCustomersFilteredPaginated(CustomerFilters filters) {
        var filtered = customerRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
        return new Paginated<>(filtered.map(entityToDtoMapper::mapToCustomerReadOnlyDTO));
    }

    @Transactional
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public List<CustomerReadOnlyDTO> getCustomersFiltered(CustomerFilters filters) {
        return customerRepository.findAll(getSpecsFromFilters(filters))
                .stream().map(entityToDtoMapper::mapToCustomerReadOnlyDTO).toList();
    }

    @Transactional
    public Page<CustomerReadOnlyDTO> getSortedCustomers(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page,size, sort);
        return customerRepository.findAll(pageable).map(entityToDtoMapper::mapToCustomerReadOnlyDTO);
    }


    private Specification<Customer> getSpecsFromFilters(CustomerFilters filters) {
        return Specification
                .where(CustomerSpecification.customerStringFieldLike("lastName", filters.getLastName()))
                .and(CustomerSpecification.customerUserGenderIs(filters.getUserGender()))
                .and(CustomerSpecification.customerUserSsnIs(filters.getUserSsn()))
                .and(CustomerSpecification.customerUserIsActive(filters.getActive()));
    }

    @Transactional
    public void deleteCustomer(Long id) throws AppObjectNotFoundException {
        if (!customerRepository.existsById(id)) {
            throw new AppObjectNotFoundException("Customer", "Customer not found with ID: " + id);
        }
        customerRepository.deleteById(id);
    }

    public CustomerReadOnlyDTO updateCustomer(Long id, CustomerInsertDTO customerDTO) {
        return null;
    }

    public UserReadOnlyDTO getCustomerById(Long id) throws AppObjectNotFoundException {
        // Βρες τον χρήστη μέσω του ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("Customer", "Customer not found with ID: " + id));

        // Μετέτρεψε τον User σε UserReadOnlyDTO μέσω του Mapper
        return entityToDtoMapper.mapToUserReadOnlyDTO(user);
    }


}



