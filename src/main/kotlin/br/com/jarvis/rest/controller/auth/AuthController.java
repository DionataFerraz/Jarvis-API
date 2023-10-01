package br.com.jarvis.rest.controller.auth;



import br.com.jarvis.config.security.TokenService;
import br.com.jarvis.domain.entity.UserEntity;
import br.com.jarvis.service.auth.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final LoginService loginService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginInput) {
        try {
            UsernamePasswordAuthenticationToken userAuthDTO = new UsernamePasswordAuthenticationToken(loginInput.getEmail(), loginInput.getPassword());
            Authentication authentication = authenticationManager.authenticate(userAuthDTO);
            Object principal = authentication.getPrincipal();
            UserEntity login = (UserEntity) principal;
            String token = tokenService.generateToken(login);

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Verifique suas credenciais!", HttpStatus.FORBIDDEN);
        }


    }

    @PostMapping("/register")
    public ResponseEntity<LoginOutput> register(@RequestBody @Valid LoginCreateRequest data) {

        LoginOutput loginOutput = loginService.createUser(data);

        return new ResponseEntity<>(loginOutput, HttpStatus.CREATED);
    }
}
