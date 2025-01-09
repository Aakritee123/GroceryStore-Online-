package com.egroc.service;
import com.egroc.enums.UserRole;
import com.egroc.model.User;
import com.egroc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;


@EnableJpaRepositories(basePackages = "com.bloodorganproject.app.Repository")
@Component
public class AdminInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner initializeAdmin() {

        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setName("Admin");
                admin.setUsername("admin");
                admin.setEmail("admin100@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(UserRole.ADMIN);
                userRepository.save(admin);
                System.out.println("Admin user created");
            }
        };

    }
}
