package gr.aueb.cf.myproject.repository;

import gr.aueb.cf.myproject.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {

    Optional<Admin> findByUserId(Long id);
    Optional<Admin> findByUuid(Long id);
}
