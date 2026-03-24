package com.example.umcspringbootstudy.user.repository;

import com.example.umcspringbootstudy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
