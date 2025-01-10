package gr.aueb.cf.myproject.repository;

import gr.aueb.cf.myproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {


    Optional<Customer> findByDiscountCardNumber(String discountCardNumber);


}
