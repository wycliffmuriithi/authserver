package com.wyki.idsauth.services.dao;

import com.wyki.idsauth.db.OauthclientdetailsRepo;
import com.wyki.idsauth.db.entities.OauthclientDetails;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class name: ResourceDao
 * Creater: wgicheru
 * Date:1/30/2020
 */
@Service
public class ResourceDao {
    @Autowired
    OauthclientdetailsRepo oauthclientdetailsRepo;
    private static final Logger LOGGER = Logger.getLogger(ResourceDao.class);

    /**
     * the name provides values for resourceid and clientid
     * the secret should already be BCRYPT Encoded
     * @param name
     * @param secret
     */
    public void createResource(String name,String secret){
        LOGGER.info("creating resource "+name);
        if(oauthclientdetailsRepo.countByResourceIds(name)<1){
            OauthclientDetails oauthclientDetails = new OauthclientDetails();
            oauthclientDetails.setResourceIds(name);
            oauthclientDetails.setClientId(name.concat("_clientid"));
            oauthclientDetails.setClientSecret(secret);
            oauthclientDetails.setScope("read,write");
            oauthclientDetails.setAuthorizedGrantTypes("password,authorization_code,refresh_token,client_credentials");
            oauthclientDetails.setAccessTokenValidity(36000);
            oauthclientDetails.setRefreshTokenValidity(36000);
            oauthclientDetails.setAutoapprove("true");

            oauthclientdetailsRepo.save(oauthclientDetails);
        }else{
            LOGGER.info("resource already exists");
        }

    }
}
