package com.wyki.idsauth.services.messagebroker;

import com.wyki.idsauth.services.dao.ResourceDao;
import com.wyki.idsauth.services.dao.UsersDao;
import com.wyki.idsauth.wrappers.*;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * Class name: MessageListener
 * Creater: wgicheru
 * Date:6/10/2019
 */
@Service
public class MessageListener {
    private static final Logger LOGGER = Logger.getLogger(MessageListener.class);
    UsersDao usersDao;
    ResourceDao resourceDao;

    public MessageListener(UsersDao usersDao, ResourceDao resourceDao) {
        this.usersDao = usersDao;
        this.resourceDao = resourceDao;
    }


//    @RabbitListener(queues = "createuser")
    public void receiveUsercreationRequest(MessageBrokerUserCreation message) {
        LOGGER.info(message);
        usersDao.registerUser(message.getFirstname(), message.getOthernames(), message.getEmail(), message.getPhonenumber(),
                message.getDateofbirth(), message.getGender(), message.getNationality(), message.getNationalidnumber(),
                message.getResourceid(),message.getRolename());
    }

//    @RabbitListener(queues = "updatepassword")
    public void receiveUpdatePassword(UpdateuserPassword updateuserPassword ){
        LOGGER.info(updateuserPassword);
        usersDao.updateUserPassword(updateuserPassword.getEmail(),updateuserPassword.getPhonenumber(),
                updateuserPassword.getPassword());
    }

//    @RabbitListener(queues = "deactivateuser")
    public void deactivateuserAccount(UserDetailsWrapper userDetailsWrapper){
        LOGGER.info(userDetailsWrapper);
        usersDao.deactivateUser(userDetailsWrapper.getEmail(), userDetailsWrapper.getPhonenumber());
    }

//    @RabbitListener(queues = "updatephoneandemail")
    public void updateUserDetails(UserDetailsWrapper rabbitupdatePhoneandEmail){
        LOGGER.info(rabbitupdatePhoneandEmail);
        usersDao.updatePhoneandEmail(rabbitupdatePhoneandEmail.getUserid(),rabbitupdatePhoneandEmail.getPhonenumber(),
                rabbitupdatePhoneandEmail.getEmail());
    }
}
