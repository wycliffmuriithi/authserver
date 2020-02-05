package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.OauthclientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class name: OauthclientdetailsRepo
 * Creater: wgicheru
 * Date:6/18/2019
 */
public interface OauthclientdetailsRepo extends JpaRepository<OauthclientDetails,String> {
    int countByResourceIds(String resourceid);
}
