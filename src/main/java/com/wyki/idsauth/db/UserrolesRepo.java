package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.Userroles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * interface name: UserrolesRepo
 * Creater: wgicheru
 * Date:3/2/2020
 */
public interface UserrolesRepo extends JpaRepository<Userroles,Long> {
    @Query(nativeQuery = true,value =
            "SELECT * FROM miniagri_usersroles ur WHERE ur.userid=:userid AND ur.roleid=:roleid")
    Optional<Userroles> findbyUserRole(@Param("userid") long userid,@Param("roleid") long roleid);

    @Query(nativeQuery = true,value =
            "SELECT COUNT(*) FROM miniagri_usersroles ur WHERE ur.userid=:userid AND ur.roleid=:roleid")
   Long countbyUserRole(@Param("userid") long userid,@Param("roleid") long roleid);
    @Query(nativeQuery = true,
            value = "SELECT name FROM miniagri_usersroles ur JOIN miniagri_grouproles mgr ON ur.grouproleid=mgr.id " +
                    "JOIN miniagri_rolesconfig mrc ON mgr.roleid=mrc.roleid WHERE ur.userid=:userid")
    List<String> getRolesbyUser(@Param("userid") Long userid);
}
