package est.una.ac.cr.backend.config;

import est.una.ac.cr.backend.entity.Usuario;
import est.una.ac.cr.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.seed.admin-password:changeit}")
    private String adminPassword;

    @Value("${app.seed.registrador-password:changeit}")
    private String registradorPassword;

    @Value("${app.seed.visor-password:changeit}")
    private String visorPassword;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario("admin", passwordEncoder.encode(adminPassword), "ADMIN");
                Usuario registrador = new Usuario("registrador", passwordEncoder.encode(registradorPassword), "REGISTRADOR");
                Usuario visor = new Usuario("visor", passwordEncoder.encode(visorPassword), "VISOR");
                usuarioRepository.save(admin);
                usuarioRepository.save(registrador);
                usuarioRepository.save(visor);
                System.out.println("Usuarios iniciales creados");
            }
        };
    }
}