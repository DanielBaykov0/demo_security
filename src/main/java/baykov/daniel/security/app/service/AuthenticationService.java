package baykov.daniel.security.app.service;

import baykov.daniel.security.app.payload.dto.LoginDto;
import baykov.daniel.security.app.payload.dto.RegisterDto;

public interface AuthenticationService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
