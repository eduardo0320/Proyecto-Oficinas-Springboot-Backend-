package est.una.ac.cr.backend.controller;

import est.una.ac.cr.backend.entity.Usuario;
import est.una.ac.cr.backend.repository.UsuarioRepository;
import est.una.ac.cr.backend.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsername(usuario.getUsername());

        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();
            if (passwordEncoder.matches(usuario.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getUsername(), user.getRole());
                return ResponseEntity.ok(token);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }

    @GetMapping("/rol")
    public ResponseEntity<String> getRol(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no proporcionado");
        }

        String token = authHeader.substring(7);
        String rol = jwtService.extractRole(token);

        return ResponseEntity.ok(rol);
    }
}

