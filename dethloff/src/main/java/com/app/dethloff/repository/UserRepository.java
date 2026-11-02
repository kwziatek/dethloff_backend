package com.app.dethloff.repository;

import com.app.dethloff.model.UserEntity;
import java.util.Optional;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findByUsername(String username);

  @NullMarked
  Optional<UserEntity> findById(String id);
}
