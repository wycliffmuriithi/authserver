package com.wyki.idsauth.services.rabbitmq;

import com.wyki.idsauth.services.dao.ResourceDao;
import com.wyki.idsauth.services.dao.UsersDao;
import com.wyki.idsauth.wrappers.RabbitResourceCreation;
import com.wyki.idsauth.wrappers.RabbitUpdateuserPassword;
import com.wyki.idsauth.wrappers.RabbitUserCreation;
import com.wyki.idsauth.wrappers.RabbitUseraccountWrapper;
import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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


    @RabbitListener(queues = "createuser")
    public void receiveUsercreationRequest(RabbitUserCreation message) {
        LOGGER.info(message);
        usersDao.registerUser(message.getFirstname(), message.getOthernames(), message.getEmail(), message.getPhonenumber(),
                message.getDateofbirth(), message.getGender(), message.getNationality(), message.getNationalidnumber(), message.getResourceid());
    }

    @RabbitListener(queues = "createresource")
    public void receiveResourcecreationRequest(RabbitResourceCreation message) {
        LOGGER.info(message);
        resourceDao.createResource(message.getResourcename(), message.getSecret());
    }

    @RabbitListener(queues = "updatepassword")
    public void receiveUpdatePassword(RabbitUpdateuserPassword updateuserPassword ){
        LOGGER.info(updateuserPassword);
        usersDao.updateUserPassword(updateuserPassword.getEmail(),updateuserPassword.getPhonenumber(),
                updateuserPassword.getPassword());
    }

    @RabbitListener(queues = "deactivateuser")
    public void deactivateuserAccount(RabbitUseraccountWrapper rabbitUseraccountWrapper){
        LOGGER.info(rabbitUseraccountWrapper);
        usersDao.deactivateUser(rabbitUseraccountWrapper.getEmail(),rabbitUseraccountWrapper.getPhonenumber());
    }
}
