//package com.wyki.idsauth.configs;
//
//import com.wyki.idsauth.db.OauthclientdetailsRepo;
//import com.wyki.idsauth.db.UsersRepo;
//import com.wyki.idsauth.db.entities.OauthclientDetails;
//import com.wyki.idsauth.db.entities.Users;
//import org.jboss.logging.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Random;
//
///**
// * Class name: TestClass
// * Creater: wgicheru
// * Date:6/18/2019
// */
//@Service
//@EnableScheduling
//public class CreateResourceserver {
//    @Autowired
//    OauthclientdetailsRepo oauthclientdetailsRepo;
////    @Autowired
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    @Autowired
//    UsersRepo usersRepo;
//
//    private  Logger LOGGER = Logger.getLogger(CreateResourceserver.class);
//
//    @Scheduled(fixedRate = 1000 * 5,initialDelay = 5000)
//    public void runtest(){
//        //insert a bcrypt encoded record to db
//        OauthclientDetails oauthclientDetails = new OauthclientDetails();
//
//        oauthclientDetails.setAccessTokenValidity(36000);
//        oauthclientDetails.setAdditionalInformation(null);
//        oauthclientDetails.setAuthorities(null);
//        oauthclientDetails.setAuthorizedGrantTypes("password,authorization_code,refresh_token,client_credentials");
//        oauthclientDetails.setAutoapprove("true");
//        oauthclientDetails.setClientId("staffapp_clientid_"+new Random().nextInt(1000));
//        oauthclientDetails.setClientSecret(encoder.encode("secret"));
//        oauthclientDetails.setRefreshTokenValidity(36000);
//        oauthclientDetails.setResourceIds("staffapp");
//        oauthclientDetails.setScope("read,write");
//        oauthclientDetails.setWebServerRedirectUri(null);
//
//        oauthclientdetailsRepo.save(oauthclientDetails);
//
//
//
//        Users users = new Users();
//        users.setActive(true);
//        users.setEmail("wycliff.muriithi@kra.go.ke");
//        users.setPhonenumber("254715702886");
//        users.setPassword(encoder.encode("password"));
//
//        usersRepo.save(users);
//
////        StationUsers stationUsers = new StationUsers();
//
//        LOGGER.info("done inserting record");
//    }
//}
