package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.GroupRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrouprolesRepo extends JpaRepository<GroupRoles,Long> {
    @Query("SELECT COALESCE(0, COUNT(gr)) FROM GroupRoles gr WHERE gr.groupid=:groupid AND gr.roleid=:roleid")
    Long countbyMapping(@Param("groupid") Long groupid, @Param("roleid") Long roleid);

}
