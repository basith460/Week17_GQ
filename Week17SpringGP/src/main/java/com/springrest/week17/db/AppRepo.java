package com.springrest.week17.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AppRepo extends JpaRepository<AppUser, Integer>{
	
	Optional<AppUser> findByName(String name);
}
