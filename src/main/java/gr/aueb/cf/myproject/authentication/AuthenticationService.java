package gr.aueb.cf.myproject.authentication;


import gr.aueb.cf.myproject.core.exceptions.AppObjectNotAuthorizedException;
import gr.aueb.cf.myproject.dto.AuthenticationRequestDTO;
import gr.aueb.cf.myproject.dto.AuthenticationResponseDTO;
import gr.aueb.cf.myproject.model.User;
import gr.aueb.cf.myproject.repository.UserRepository;
import gr.aueb.cf.myproject.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private  final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) throws AppObjectNotAuthorizedException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword()));

                User user = userRepository.findByUsername(authentication.getName())
                        .orElseThrow(()->new AppObjectNotAuthorizedException("User", "User not Authorized"));

                String token = jwtService.generateToken(authentication.getName(), user.getRole().name());
                return new AuthenticationResponseDTO(user.getFirstName(), user.getLastName(), token);

    }
}
