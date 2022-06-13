package com.wyki.idsauth.db.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name="miniagri_organizations")
@Entity
@Data
public class Organizations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;

    @CreationTimestamp
    Date creationdate;
}
