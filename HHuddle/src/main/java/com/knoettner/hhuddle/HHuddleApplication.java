package com.knoettner.hhuddle;

import com.knoettner.hhuddle.dto.*;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.knoettner.hhuddle.UserRole.RESIDENT;

@SpringBootApplication
public class HHuddleApplication implements CommandLineRunner  {
@Autowired
    AdminService adminService;



    public static void main(String[] args) {
        SpringApplication.run(HHuddleApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        //Testing Admin Service
        // createUser done
        //createAdminPost done
        //create new Facility done
        //UpdateFacility done
        //deleteFacility done

        //all methods regarding house NOT WORKING
        //deletePost not working (USERKEYPOST?!)
        //getPosts not working USERKEYPOST"?
        //deleteUser not wokring!
        //getFacilites byhouse

       /* Chat chat1 = new Chat();
        House house = new House(null, "Hausstraße 1", null, null, null);
        Role role = new Role(null,RESIDENT, null );
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        MyUser user = new MyUser(null, "test", "test@test.at", "Top_1", roleSet, null, null, null, null,  house );

        FacilityDto facility1 = new FacilityDto(null, "Pool", null, null, null);
        Set<FacilityDto> set = new HashSet<>();
        set.add(facility1);
        HouseDto houseDto = new HouseDto(null,"Hausstraße 12", new HashSet<>(), set);

         adminService.createHouse(houseDto);


 MyUserDto userDto = new MyUserDto(null, "raffy@test.at","TOP05",1L);
        adminService.createUser(userDto);
        BasicUserDto basic = new BasicUserDto(1L, "TOP03");
        PostDto post = new PostDto(null, "Hilfe gesucht","Brauche Hilfe beim Programmieren", "FRONTPAGE" , LocalDateTime.now(), false, null, null, null, false, null, basic, 1L);

        adminService.createFacility(new FacilityDto(1L, "Pool", null, 1L, new HashSet<>()));
        adminService.updateFacility(new FacilityDto(1L, "SwimmingPool", null, 1L, new HashSet<>()));
        adminService.deleteFacilityById(1L);

        */
        MyUserDto userDto = new MyUserDto(2L, "test@gmail.com","TOP05",1L);
adminService.updateUser(userDto);
    }

}
