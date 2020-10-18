package com.dibasb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dibasb.entities.UserMs;

@Repository
public interface UserRepository extends JpaRepository<UserMs, Long>
{
	Optional<UserMs> findByEmail(String email);
	
}
