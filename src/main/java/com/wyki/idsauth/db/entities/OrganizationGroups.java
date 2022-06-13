package com.wyki.idsauth.db.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name="miniagri_orggroups")
@Entity
@Data
public class OrganizationGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    Long groupid;
    @CreationTimestamp
    Date creationdate;
}
