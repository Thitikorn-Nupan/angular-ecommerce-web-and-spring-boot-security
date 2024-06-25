package com.ttknpdev.backend.repositories.secure;

import com.ttknpdev.backend.entities.secure.User;
import org.springframework.data.repository.CrudRepository;

// use passed @service So it do not need to use @Repository
public interface UserRepository extends CrudRepository<User,Long> {
    // behind the sens ... auto
    User findByUsername(String username);
}
