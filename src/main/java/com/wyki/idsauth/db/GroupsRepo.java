package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupsRepo extends JpaRepository<Groups,Long> {
    @Query("SELECT COUNT(g) FROM Groups g WHERE g.name=:groupname")
    Long countByName(@Param("groupname") String name);
}
