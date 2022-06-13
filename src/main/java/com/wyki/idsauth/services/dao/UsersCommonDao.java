package com.wyki.idsauth.services.dao;

import com.wyki.idsauth.db.UserrolesRepo;
import com.wyki.idsauth.db.UsersRepo;
import com.wyki.idsauth.db.entities.Users;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsersCommonDao {
    @Autowired
    public UsersRepo dbusersRepo;
    @Autowired
    UserrolesRepo userrolesRepo;

    @Transactional
    public Optional<Users> loadUserByusername(String username) {
        List<Users> dbusersList = dbusersRepo.findByEmailOrPhonenumberOrNationalidnumber(username, username, username);
        if (dbusersList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(dbusersList.get(0));
        }
    }

    public Optional<Users> loadbyUserid(Long userid){
        return dbusersRepo.findById(userid);
    }

    public List<String> getRolesbyUser(Long userid){
        return userrolesRepo.getRolesbyUser(userid);
    }

    public Long getUseridfromOptional(String username){
        return loadUserByusername(username).get().getUserid();
    }
}
