package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.ActiveDirectory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivedirectoryRepo extends JpaRepository<ActiveDirectory,Long> {
    List<ActiveDirectory> findByStaffname(String name);
}
