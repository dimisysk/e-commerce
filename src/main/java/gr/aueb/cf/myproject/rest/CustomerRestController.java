package gr.aueb.cf.myproject.rest;

import gr.aueb.cf.myproject.core.exceptions.*;
import gr.aueb.cf.myproject.core.filters.CustomerFilters;
import gr.aueb.cf.myproject.core.filters.Paginated;
import gr.aueb.cf.myproject.dto.CustomerInsertDTO;
import gr.aueb.cf.myproject.dto.CustomerReadOnlyDTO;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRestController.class);
    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<Page<CustomerReadOnlyDTO>> getAllCustomer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size ) {
        Page<CustomerReadOnlyDTO> customersPage = customerService.getPaginatedCustomers(page,size);
        return new ResponseEntity<>(customersPage, HttpStatus.OK);
    }

    @PostMapping("/save-customer")
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerInsertDTO customerDTO,
                                                            BindingResult bindingResult)
            throws AppObjectInvalidArgumentException, ValidationException, AppObjectAlreadyExists {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        try {
            CustomerReadOnlyDTO customerReadOnlyDTO = customerService.saveCustomer(customerDTO);
            URI location = URI.create("/api/customers/" + customerReadOnlyDTO.getUser().getId());
            return ResponseEntity.created(location).body(customerReadOnlyDTO);
        } catch (AppObjectAlreadyExists e) {
            // Δημιουργία custom response για την εξαίρεση
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("code", e.getCode());
            errorDetails.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorDetails);
        }

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

}
//public ResponseEntity<TeacherReadOnlyDTO> addTeacher(@Valid @RequestBody TeacherInsertDTO insertDTO, BindingResult bindingResult)  {
//    insertValidator.validate(insertDTO,bindingResult);
//    if (bindingResult.hasErrors()) {
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//    try {
//        Teacher teacher = teacherService.insertTeacher(insertDTO);
//        TeacherReadOnlyDTO teacherReadOnlyDTO = Mapper.mapToReadOnlyDTO(teacher);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(teacherReadOnlyDTO.getId())
//                .toUri();
//        return ResponseEntity.created(location).body(teacherReadOnlyDTO);
//
//    } catch (Exception e) {
//        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
//    }
//}