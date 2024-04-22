package com.Ducat.DemoAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ducat.DemoAPI.Entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	
}
