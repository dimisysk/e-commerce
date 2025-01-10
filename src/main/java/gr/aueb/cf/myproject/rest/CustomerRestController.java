package gr.aueb.cf.myproject.rest;

import gr.aueb.cf.myproject.core.exceptions.*;
import gr.aueb.cf.myproject.core.filters.CustomerFilters;
import gr.aueb.cf.myproject.core.filters.Paginated;
import gr.aueb.cf.myproject.dto.CustomerInsertDTO;
import gr.aueb.cf.myproject.dto.CustomerReadOnlyDTO;
import gr.aueb.cf.myproject.dto.UserReadOnlyDTO;
import gr.aueb.cf.myproject.repository.UserRepository;
import gr.aueb.cf.myproject.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRestController.class);
    private final CustomerService customerService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<CustomerReadOnlyDTO>> getAllCustomer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size ) {
        Page<CustomerReadOnlyDTO> customersPage = customerService.getPaginatedCustomers(page,size);
        return new ResponseEntity<>(customersPage, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create")
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerInsertDTO customerDTO,
                                                            BindingResult bindingResult)
            throws AppObjectInvalidArgumentException, ValidationException, AppObjectAlreadyExists {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        try {
            CustomerReadOnlyDTO customerReadOnlyDTO = customerService.saveCustomer(customerDTO);
            URI location = URI.create("/api/customers/" + customerReadOnlyDTO.getUser().getId());
            System.out.println("Payload received: " + customerDTO);
            return ResponseEntity.created(location).body(customerReadOnlyDTO);
        } catch (AppObjectAlreadyExists e) {
            // Δημιουργία custom response για την εξαίρεση
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("code", e.getCode());
            errorDetails.put("message", e.getMessage());
            return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
//            return ResponseEntity.badRequest().body(errorDetails);
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerReadOnlyDTO> updateCustomer(@PathVariable Long id,
                                                              @Valid @RequestBody CustomerInsertDTO customerDTO,
                                                              BindingResult bindingResult)
            throws AppObjectNotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        CustomerReadOnlyDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) throws AppObjectNotFoundException {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReadOnlyDTO> getCustomerById(@PathVariable Long id) throws AppObjectNotFoundException {
        UserReadOnlyDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }





    @PostMapping("/customers/all")
    public ResponseEntity<List<CustomerReadOnlyDTO>> getTeachers(@Nullable @RequestBody CustomerFilters filters, Principal principal)
            throws AppObjectNotFoundException, AppObjectNotAuthorizedException {
        try {
            if (filters == null) filters = CustomerFilters.builder().build();
            return ResponseEntity.ok(customerService.getCustomersFiltered(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get teachers.", e);
            throw e;
        }
    }


    @GetMapping("/check-duplicate-username/{username}")
    public ResponseEntity<Map<String, String>> checkDuplicateUsername(@PathVariable String username) {
        Map<String, String> response = new HashMap<>();
        try {
            boolean exists = userRepository.existsByUsername(username);
            if (exists) {
                response.put("msg", "Username already in use");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            response.put("msg", "Username available");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("msg", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
