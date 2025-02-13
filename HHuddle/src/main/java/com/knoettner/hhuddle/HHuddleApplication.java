package com.knoettner.hhuddle;

import com.knoettner.hhuddle.dto.CreateAdminDto;
import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.PostRepository;
import com.knoettner.hhuddle.repository.RoleRepository;
import com.knoettner.hhuddle.repository.UserPostRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import com.knoettner.hhuddle.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.knoettner.hhuddle.UserRole.*;

@SpringBootApplication
@EnableMethodSecurity
public class HHuddleApplication implements CommandLineRunner {
    @Autowired
    AdminService adminService;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPostRepository userPostRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    PasswordEncoder encoder;


    public static void main(String[] args) {
        SpringApplication.run(HHuddleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //Hardcoded Roles: 1 for RESIDENT, 2 for ADMIN/P_MANAGEMENT
        Optional<Role> maybeRole = roleRepository.findById(1L);
        if (maybeRole.isEmpty()) {
            Role resident = new Role(1L, ROLE_RESIDENT, new HashSet<>());
            roleRepository.save(resident);
        }
        Optional<Role> maybeRole2 = roleRepository.findById(2L);
        if (maybeRole2.isEmpty()) {
            Role pManagement = new Role(2L, ROLE_PMANAGEMENT, new HashSet<>());
            roleRepository.save(pManagement);
        }

        //Hardcoded Test Admin: Hausverwaltung_Mayer
        Optional<MyUser> maybeAdmin = userRepository.findByUsername("Hausverwaltung_Mayer");
        if (maybeAdmin.isEmpty()) {
            CreateAdminDto createAdminDto = new CreateAdminDto(1L, "admin", "admin@hausverwaltungmayer.at", "Hausverwaltung_Mayer", null, true);
            try {
                adminService.createAdminUser(createAdminDto);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}




