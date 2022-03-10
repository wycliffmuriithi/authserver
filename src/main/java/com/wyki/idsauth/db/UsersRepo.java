package com.wyki.idsauth.db;

import com.wyki.idsauth.db.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    List<Users> findByEmailOrPhonenumberAndActiveIsTrue(String email, String phonenumber);
    List<Users> findByEmailOrPhonenumber(String email, String phonenumber);
    List<Users> findByEmailOrPhonenumberOrNationalidnumber(String email, String phonenumber,String nationalidnumber);
    List<Users> findByNationalidnumber(String nationalidnumber);

    int countByActiveTrue();
    int countByActiveFalse();

}
