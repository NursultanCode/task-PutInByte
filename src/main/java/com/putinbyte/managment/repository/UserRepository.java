package com.putinbyte.managment.repository;

import com.putinbyte.managment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
