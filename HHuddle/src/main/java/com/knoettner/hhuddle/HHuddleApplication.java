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






      /*



        UserPostKey key = new UserPostKey(102L, 52L,3L );
        adminService.deleteAdminPost(key);

        System.out.println(adminService.getAllFacilitiesByHouseId(1052L));
*/

      // MyUserDto user2 = new MyUserDto(null, "ffff@tttt.at", "Top_30",1L );
       //adminService.createUser(user2);

      //BasicUserDto user1 = new BasicUserDto(1L, "Top_30");



     // adminService.deleteAdminPost(2L);

        //adminService.deleteHouseById(1L);

       //

        //MyUserDto user2 = new MyUserDto(null, "zug@test.at", "Top_119",1L );
        //MyUserDto user = adminService.createUser(user2);
        //user.setMail("zug@budapest.at");
        //adminService.updateUser(user);

       // HouseDto house = new HouseDto(null, "Nebengasse 202", new HashSet<>(), new HashSet<>());
       // adminService.createHouse(house);

        //HouseDto house2 = new HouseDto(null, "Wiener Straße 9", new HashSet<>(), new HashSet<>());
        //adminService.createHouse(house2);
        //MyUserDto user2 = new MyUserDto(null, "resident122@gmx.at", "Top_99",2L );
        //MyUserDto user = adminService.createUser(user2);

      //  FacilityDto facility = new FacilityDto(null, "Pool", "3 bahnen a 25m", 2L, new HashSet<>());
        //adminService.createFacility(facility);
     //   FacilityDto facilityDto  = new FacilityDto(null, "Gemeinschaftsraum ", "Raum mit Küchenzeile und Toilette",2L, new HashSet<>());
       //FacilityDto updateFacility = adminService.createFacility(facilityDto);
        //updateFacility.setType("Gemeinschaftsraum Green");
        //adminService.updateFacility(updateFacility);
        //System.out.println(adminService.getAllFacilitiesByHouseId(2L));
        //System.out.println(adminService.getAllHouses());
        //adminService.deleteHouseById(102L);
        //System.out.println(adminService.getAllHouses());
       // System.out.println(adminService.getAdminBoardIdByHouseId(2L));
     //   BasicUserDto user = new BasicUserDto(302L, "Top_04");
       // PostDto post = new PostDto(null,"Test2", "Test2", FRONTPAGE.toString(), LocalDateTime.now(), false, null, null, null, false, null, user , 56L);
       // adminService.createAdminPost(post);
//adminService.getAdminPostsByHouseId(2L);
adminService.deleteAdminPost(302L);
    }
}
