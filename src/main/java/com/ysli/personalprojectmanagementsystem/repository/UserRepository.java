package com.ysli.personalprojectmanagementsystem.repository;

import com.ysli.personalprojectmanagementsystem.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
