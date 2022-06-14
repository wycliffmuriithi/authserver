package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.OrganizationGroups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrganizationgroupsRepo extends JpaRepository<OrganizationGroups,Long> {
    @Query("SELECT COUNT(og) FROM OrganizationGroups og WHERE og.groupid=:groupid AND og.organizationid=:organizationid")
    Long countbyMapping(@Param("groupid") Long groupid, @Param("organizationid") Long organizationid);

    Page<OrganizationGroups> findAllByOrganizationid(Pageable pageable,Long organizationid);
}
