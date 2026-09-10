package com.cfs.Ecomm.Repo;

import com.cfs.Ecomm.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
