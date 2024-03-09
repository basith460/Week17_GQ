package com.springrest.week17;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springrest.week17.db.AppService;
import com.springrest.week17.db.AppUser;

@SpringBootApplication
public class Week17SpringGpApplication implements CommandLineRunner{
	
	@Autowired
	AppService appService;
	
	public static void main(String[] args) {
		SpringApplication.run(Week17SpringGpApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Set<String> authAdmin=new HashSet<>();
		authAdmin.add("Admin");
		
		Set<String> authUser=new HashSet<>();
		authUser.add("User");
		
		PasswordEncoder en=new BCryptPasswordEncoder();
		
		AppUser appAdmin=new AppUser(1,"admin",en.encode("admin"),authAdmin);
		
		appService.add(appAdmin);
		
		AppUser appUser=new AppUser(2,"user",en.encode("user"),authUser);
		//$2a$10$2zVPPP1Z7cjcMnIthLhbuO397JWGTDSiTEvNUsjNU6zl5GSWLeuPa
		appService.add(appUser);
	}

}
