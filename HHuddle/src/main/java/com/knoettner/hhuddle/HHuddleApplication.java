package com.knoettner.hhuddle;

import com.knoettner.hhuddle.dto.CreateUpdateUserDto;
import com.knoettner.hhuddle.dto.HouseDto;
import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.RoleRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import com.knoettner.hhuddle.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.HashSet;
import java.util.Optional;

import static com.knoettner.hhuddle.UserRole.P_MANAGEMENT;
import static com.knoettner.hhuddle.UserRole.RESIDENT;

@SpringBootApplication
@EnableMethodSecurity
public class HHuddleApplication implements CommandLineRunner  {
@Autowired
    AdminService adminService;

@Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    public static void main(String[] args) {
        SpringApplication.run(HHuddleApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {

        //Hardcoded Roles: 1 for RESIDENT, 2 for ADMIN/P_MANAGEMENT
        Optional<Role> maybeRole = roleRepository.findById(1L);
        if (maybeRole.isEmpty()) {
            Role resident = new Role(1L, RESIDENT, new HashSet<>());
            roleRepository.save(resident);
        }
        Optional<Role> maybeRole2 = roleRepository.findById(2L);
        if (maybeRole2.isEmpty()) {
            Role pManagement = new Role(2L, P_MANAGEMENT, new HashSet<>());
            roleRepository.save(pManagement);
        }

        //Hardcoded Test Admin: Hausverwaltung_Mayer
        Optional<MyUser> maybeAdmin = userRepository.findByUsername("Hausverwaltung_Mayer");
        if (maybeAdmin.isEmpty()) {
            CreateUpdateUserDto createUpdateUserDto = new CreateUpdateUserDto(null, "admin", "test@hausverwaltungstest.at", "Hausverwaltung_Mayer", null, null);
            try {
                adminService.createAdminUser(createUpdateUserDto);
            } catch (Exception e) {
                System.out.println(e);
            }
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



     //  HouseDto house = new HouseDto(null, "An der Baustelle 116", new HashSet<>(), new HashSet<>());
//       adminService.createHouse(house);

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
        //MyUserDto user2 = new MyUserDto(null, "konstantin@test.at", "Top_31",52L );
        //MyUserDto user = adminService.createUser(user2);
     //   BasicUserDto user = new BasicUserDto(302L, "Top_04");
      // PostDto post = new PostDto(null,"Ankündigung", "Das ist eine Ankündigung", FRONTPAGE.toString(), LocalDateTime.now(), false, null, null, null, false, null, user , 56L);
       //adminService.createAdminPost(post);
      //  System.out.println(adminService.getAdminPostsByHouseId(2L));
//adminService.deleteAdminPost(552L);

       // MyUserDto user2 = new MyUserDto(null, "konstantin@test.at", "Top_31",52L );
        //MyUserDto user = adminService.createUser(user2);
        //user.setMail("konsti@lachi.at");

        //adminService.updateUser(user);
      //  System.out.println(adminService.getAllHouses());


    }
}
