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
@Table(name="RolesConfig")
@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
public class Roles implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long roleid;
    String name;
    @Column(columnDefinition = "longtext")
    String rules;
    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StationUsers> stationusers;
}
