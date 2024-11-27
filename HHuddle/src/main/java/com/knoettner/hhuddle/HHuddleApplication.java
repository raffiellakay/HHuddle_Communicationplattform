package com.knoettner.hhuddle;

import com.knoettner.hhuddle.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

import static com.knoettner.hhuddle.UserRole.RESIDENT;

@SpringBootApplication
public class HHuddleApplication implements CommandLineRunner  {

    public static void main(String[] args) {
        SpringApplication.run(HHuddleApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        Chat chat1 = new Chat();
        House house = new House(null, "Hausstraße 1", null, null, null);
        Role role = new Role(null,RESIDENT, null );
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        MyUser user = new MyUser(null, "test", "test@test.at", "Top_1", roleSet, null, null, null, null,  house );
        Post post = new Post();

    }

}
