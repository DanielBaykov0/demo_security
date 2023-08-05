package baykov.daniel.security.app.service.impl;

import baykov.daniel.security.app.entity.Role;
import baykov.daniel.security.app.entity.User;
import baykov.daniel.security.app.exception.SecurityAPIException;
import baykov.daniel.security.app.payload.dto.LoginDto;
import baykov.daniel.security.app.payload.dto.RegisterDto;
import baykov.daniel.security.app.repository.RoleRepository;
import baykov.daniel.security.app.repository.UserRepository;
import baykov.daniel.security.app.security.JwtTokenProvider;
import baykov.daniel.security.app.service.AuthenticationService;
import baykov.daniel.security.app.utils.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByEmailIgnoreCase(registerDto.email())) {
            throw new SecurityAPIException(HttpStatus.BAD_REQUEST, Messages.EMAIL_EXISTS);
        }

        User user = buildUser(registerDto);
        userRepository.save(user);
        return Messages.USER_SUCCESSFULLY_REGISTERED;
    }

    private User buildUser(RegisterDto registerDto) {
        User user = new User();
        user.setFirstName(registerDto.firstName());
        user.setLastName(registerDto.lastName());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setMatchingPassword(passwordEncoder.encode(registerDto.matchingPassword()));
        user.setEmail(registerDto.email());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        Role role = new Role();
        if (userRole.isPresent()) {
            role = userRole.get();
        }
        roles.add(role);
        user.setRoles(roles);
        return user;
    }
}
