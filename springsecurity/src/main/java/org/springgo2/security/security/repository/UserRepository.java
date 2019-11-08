package org.springgo2.security.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springgo2.security.security.entity.ClientUser;

import java.util.Optional;

public interface UserRepository extends CrudRepository<ClientUser, Long> {

    Optional<ClientUser> findByUsername(String username);

}
