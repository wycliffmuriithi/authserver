package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Roles,Long> {

}
