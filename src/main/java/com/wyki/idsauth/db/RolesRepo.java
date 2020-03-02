package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepo extends JpaRepository<Roles,Long> {
    Roles findByRoleid(int roleid);
    List<Roles> findByNameAndResourceid(String rolename, String resourceid);
}
