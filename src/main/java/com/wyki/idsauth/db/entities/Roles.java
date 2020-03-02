package com.wyki.idsauth.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Class name: Roles
 * Creater: wgicheru
 * Date:6/17/2019
 */
@Table(name="rolesconfig")
@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
public class Roles implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long roleid;
    String name;
    @Column(columnDefinition = "longtext")
    String rules;
    @OneToMany(mappedBy = "roles")
    private Set<Userroles> users;
    @Column(columnDefinition = "longtext")
    String resourceid;
}
