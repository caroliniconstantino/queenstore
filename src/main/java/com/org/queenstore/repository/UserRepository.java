package com.org.queenstore.repository;

import com.org.queenstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUser(String user);

   public List<User> findAllByNameContainingIgnoreCase(@Param("name") String nome);
}