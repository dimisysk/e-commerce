package gr.aueb.cf.myproject.rest;

import gr.aueb.cf.myproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepository userRepository;

    @GetMapping("/check-duplicate-username/{username}")
    public ResponseEntity<?> checkDuplicateUsername(@PathVariable String username) {
        try {
            boolean usernameExists = userRepository.existsByUsername(username);
            if (usernameExists) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("msg", "Username already in use"));
            }
            return ResponseEntity
                    .ok(Map.of("msg", "Username available"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("msg", e.getMessage()));
        }
    }
}
