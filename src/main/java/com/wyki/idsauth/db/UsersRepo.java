package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {
    List<Users> findByEmail(String email);
}
