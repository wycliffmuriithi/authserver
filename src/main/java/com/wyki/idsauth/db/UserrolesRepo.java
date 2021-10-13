package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.Userroles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * interface name: UserrolesRepo
 * Creater: wgicheru
 * Date:3/2/2020
 */
public interface UserrolesRepo extends JpaRepository<Userroles,Long> {
    @Query(nativeQuery = true,value =
            "SELECT ur FROM users_roles ur WHERE ur.userid:=userid AND ur.roleid:=roleid")
    Optional<Userroles> findbyUserRole(@Param("userid") long userid,@Param("roleid") long roleid);
}
