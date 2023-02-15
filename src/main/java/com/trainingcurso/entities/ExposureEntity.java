package com.trainingcurso.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "exposures")
@Getter
@Setter
public class ExposureEntity {

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, length = 50)
    private String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exposure")
    private List<PostEntity> posts = new ArrayList<>();
}
