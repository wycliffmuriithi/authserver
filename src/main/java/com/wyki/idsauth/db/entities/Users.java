package com.wyki.idsauth.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Class name: Users
 * Creater: wgicheru
 * Date:6/12/2019
 */
@Table(name="users")
@Entity
@Data @ToString
@AllArgsConstructor @NoArgsConstructor
public class Users {
    private String firstname;
    @Column(columnDefinition = "longtext")
    private String password;
    private String email;
    private String phonenumber;
    private  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long  userid;
    private  String othernames;
    private Date dateofbirth;
    private String gender;
    @Column(columnDefinition = "longtext")
    private String nationality;
    @Column(columnDefinition = "longtext")
    private String nationalidnumber;
    @Column(columnDefinition = "tinyint")
    private boolean active;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date registrationdate;
    @Column(columnDefinition = "longtext")
    private String resourceid;

//    @ManyToMany
//    @JoinTable(
//            name="users_roles",
//            joinColumns = @JoinColumn(name ="userid"),
//            inverseJoinColumns = @JoinColumn(name = "roleid"))
//    private Set<Roles> roles;
}
