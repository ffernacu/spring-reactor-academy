package pe.ffernacu.spring_reactor_academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pe.ffernacu.spring_reactor_academy.security.AuthRequest;
import pe.ffernacu.spring_reactor_academy.security.AuthResponse;
import pe.ffernacu.spring_reactor_academy.security.JwtUtil;
import pe.ffernacu.spring_reactor_academy.service.IUserService;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtUtil jwtUtil;
    private final IUserService iUserService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest){
        return iUserService.searchByUser(authRequest.getUsername())
                .map(userDetails -> {
                    if(BCrypt.checkpw(authRequest.getPassword(), userDetails.getPassword())){
                        String token = jwtUtil.generateToken(userDetails);
                        return ResponseEntity.ok(new AuthResponse(token));
                    }
                    else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}