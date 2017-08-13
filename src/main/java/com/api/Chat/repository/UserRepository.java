package com.api.Chat.repository;

import com.api.Chat.domain.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  public User findByUsername(String username);

}
