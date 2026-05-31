package com.devcaleb.ead.authUser.repositories;

import com.devcaleb.ead.authUser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {



}
