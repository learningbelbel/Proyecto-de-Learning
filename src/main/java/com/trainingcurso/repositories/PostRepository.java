package com.trainingcurso.repositories;

import com.trainingcurso.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/*
 *PagingAndSortingRepository : aparte de dejar hacernos CRUD, nos deja crear pagination and ordenar Datos;
 */
@Repository
public interface PostRepository extends PagingAndSortingRepository<PostEntity,Long> {

    //Get all the posts of this user
    List<PostEntity> getByUserIdOrderByCreatedDateDesc(long userId);

    //Create SQL Consutl
    @Query(value = "SELECT * FROM posts p where p.exposure_id = :exposure and p.expiration_date >:now ORDER BY created_date DESC LIMIT 20", nativeQuery = true)
    List<PostEntity> getLastPublicPosts(@Param("exposure")long exposureId, @Param("now")Date now);

   PostEntity findById (long id);
}
