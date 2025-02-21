package it.epicode.eventmanagement.auth;



import com.github.javafaker.Faker;
import it.epicode.eventmanagement.entities.User;
import it.epicode.eventmanagement.enumerated.Role;
import it.epicode.eventmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        for (int i = 0; i < 5; i++) {
            String username = faker.name().username();
            String password = passwordEncoder.encode("password" + i);
            Role role = random.nextBoolean() ? Role.USER : Role.ORGANIZER;

            if (userService.findByUsername(username).isEmpty()) {
                userService.registerUser(username, password, role);
                System.out.println("Creato utente: " + username + " con ruolo " + role);
            }
        }


        Optional<User> adminUser = userService.findByUsername("admin");
        if (adminUser.isEmpty()) {
            userService.registerUser("admin", passwordEncoder.encode("adminpwd"), Role.ORGANIZER);
            System.out.println("Creato utente admin con ruolo ORGANIZER");
        }
    }
}