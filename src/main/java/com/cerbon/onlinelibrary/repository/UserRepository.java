package com.cerbon.onlinelibrary.repository;

import com.cerbon.onlinelibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
