package com.trainingcurso.repositories;

import com.trainingcurso.entities.ExposureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExposureRepository extends JpaRepository<ExposureEntity,Long> {
    ExposureEntity findById(long id);

}
