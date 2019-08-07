package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.StationUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationusersRepo extends JpaRepository<StationUsers,Long> {
}
