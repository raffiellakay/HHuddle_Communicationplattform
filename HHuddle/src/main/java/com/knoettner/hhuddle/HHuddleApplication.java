package com.knoettner.hhuddle;

import com.knoettner.hhuddle.dto.*;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.RoleRepository;
import com.knoettner.hhuddle.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.knoettner.hhuddle.Category.FRONTPAGE;
import static com.knoettner.hhuddle.UserRole.PManagement;
import static com.knoettner.hhuddle.UserRole.RESIDENT;

@SpringBootApplication
public class HHuddleApplication implements CommandLineRunner  {
@Autowired
    AdminService adminService;

@Autowired
    RoleRepository roleRepository;



    public static void main(String[] args) {
        SpringApplication.run(HHuddleApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        Optional<Role> maybeRole = roleRepository.findById(1L);
        if (maybeRole.isEmpty()) {
            Role resident = new Role(1L, RESIDENT, new HashSet<>());
            roleRepository.save(resident);
        }
        Optional<Role> maybeRole2 = roleRepository.findById(2L);
        if (maybeRole2.isEmpty()) {
            Role pManagement = new Role(2L, PManagement, new HashSet<>());
            roleRepository.save(pManagement);
        }

//updateFacility überprüfen!!

        //Testing Admin Service
        // createUser done
        //createAdminPost done
        //create new Facility done
        //UpdateFacility done
        //deleteFacility done
        //createHouse done
        //get AdminBoardIDbyHouseID done
        //delete Post done - with extra generated USERKEYPOST
        //getAdminPostsByHouseId done
        //delteHouseById done



        //getPosts not working USERKEYPOST"?
        //getAllFacilityByHouseID - not working, gives empty Set




      /*



        UserPostKey key = new UserPostKey(102L, 52L,3L );
        adminService.deleteAdminPost(key);

        System.out.println(adminService.getAllFacilitiesByHouseId(1052L));
*/
        //HouseDto house = new HouseDto(null, "Teststraße 12", new HashSet<>(), new HashSet<>());
        //adminService.createHouse(house);
      // MyUserDto user2 = new MyUserDto(null, "ffff@tttt.at", "Top_30",1L );
       //adminService.createUser(user2);

      //BasicUserDto user1 = new BasicUserDto(1L, "Top_30");
        //PostDto post = new PostDto(null,"Title", "Text", FRONTPAGE.toString(), LocalDateTime.now(), false, null, null, null, false, null, user1, 5L);
       //adminService.createAdminPost(post);


     // adminService.deleteAdminPost(2L);

        //adminService.deleteHouseById(1L);
        MyUserDto user2 = new MyUserDto(null, "ffff@tttt.at", "Top_30",1L );
        MyUserDto user = adminService.createUser(user2);

        adminService.updateUser(user);

    }
}
