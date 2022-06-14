package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.Organizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrganizationRepo extends JpaRepository<Organizations,Long> {
    @Query("SELECT COUNT(o) FROM Organizations o WHERE o.name=:orgname")
    Long countByName(@Param("orgname") String name);
}
