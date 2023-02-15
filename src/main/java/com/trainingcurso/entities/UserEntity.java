package com.trainingcurso.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Table(indexes = {@Index(columnList = "id", name = "index_id", unique = true),
@Index(columnList = "email", name = "index_email", unique = true)})
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;

    //cascade, means when an user is removed, all the posts will be removed it too.
    //mappedBy, is making reference to POSTENTITY.CLASS variable - private UserEntity user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PostEntity> posts = new ArrayList<>();

}
