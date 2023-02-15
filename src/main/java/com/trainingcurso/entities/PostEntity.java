package com.trainingcurso.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "posts")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)//this is to create dates automatically
public class PostEntity {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "Content", nullable = false)
    private String content;
    //Add date automatically
    @CreatedDate // this need to set up on the top this <<@EntityListeners(AuditingEntityListener.class)>>
                // Also set up @EnableJpaAuditing on the Application Main Class, this is to allow us to create date automatically
    private Date createdDate;
    @Column( nullable = false)
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id") // this is primary key from UserEntity, forward key//
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "exposure_id")// this to create the colum name in database que une las dos tables post and exposure;
    private ExposureEntity exposure;
}
