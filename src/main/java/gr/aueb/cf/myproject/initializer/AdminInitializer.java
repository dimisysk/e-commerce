package gr.aueb.cf.myproject.initializer;
import gr.aueb.cf.myproject.core.enums.GenderType;
import gr.aueb.cf.myproject.core.enums.Role;
import gr.aueb.cf.myproject.model.Admin;
import gr.aueb.cf.myproject.model.User;
import gr.aueb.cf.myproject.repository.AdminRepository;
import gr.aueb.cf.myproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            // Δημιουργία νέου admin αν δεν υπάρχει ήδη
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin1234");
            adminUser.setRole(Role.ADMIN);
            adminUser.setFirstName("firstname");
            adminUser.setLastName("lastname");
            adminUser.setEmail("admin@email.com");
            adminUser.setSsn("ssn1234");
            adminUser.setAddress("address");
            adminUser.setAddressNumber("number");
            adminUser.setCity("city");
            adminUser.setPhone("xxxxxxxxxx");
            adminUser.setZip("zip");
            adminUser.setGender(GenderType.OTHER);
            adminUser.setIsActive(true);

            // Δημιουργία Admin και σύνδεση με τον User
            Admin newAdmin = new Admin();
            newAdmin.setUser(adminUser); // Σύνδεση του Admin με τον User

            // Αποθήκευση του Admin (το CascadeType.ALL θα αποθηκεύσει και τον User)
            adminRepository.save(newAdmin);

            System.out.println("Admin user created successfully.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }

}
