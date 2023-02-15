package com.trainingcurso.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExposureDto {

    private long id;
    private String type;

    //this is to create the relation between POSTDTO and EXPOSUREDTO, when we send the information to our customer,
    //This is a List because an exposure has a lot of posts;
    private List<PostDto> posts;
}
