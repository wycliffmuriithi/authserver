package com.wyki.idsauth.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Class name: Users
 * Creater: wgicheru
 * Date:6/12/2019
 */
@Table(name="miniagri_users")
@Entity
@Data @ToString
@AllArgsConstructor @NoArgsConstructor
public class Users {
    private String name;
    @Column(columnDefinition = "VARCHAR(1024)")
    private String password;
    private String email;
    private String phonenumber;
    private  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long  userid;
    private Date dateofbirth;
    private String gender;
    private String nationality;
    private String nationalidnumber;

    private boolean active;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date registrationdate;
    private Long organizationid;
    private int attempts;


    String otp;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    boolean validotp;
    Date otpexpirytime;

    private String createdby;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Userroles> roles;
}
