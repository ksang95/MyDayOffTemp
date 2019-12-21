package com.team4.dayoff.repository;

import com.team4.dayoff.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findBySocialIdAndRoleNot(String socialId, String role);
    
    @Transactional
    @Modifying
	@Query("UPDATE Users SET role='withdraw' WHERE id= :userId")
    void withdrawUser(int userId);
}
