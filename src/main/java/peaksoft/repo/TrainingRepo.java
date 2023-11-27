package peaksoft.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Training;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingRepo extends JpaRepository<Training, Long> {
    @Query("SELECT t FROM Training t " +
            "WHERE t.trainee.user.userName = :username " +
            "AND t.trainingDate BETWEEN :periodFrom AND :periodTo " +
            "AND t.trainer.user.firstName = :trainerName " +
            "AND t.trainingType.trainingTypName = :trainingType")
    List<Training> findTrainings(
            @Param("username") String username,
            @Param("periodFrom") LocalDate periodFrom,
            @Param("periodTo") LocalDate periodTo,
            @Param("trainerName") String trainerName,
            @Param("trainingType") String trainingType);
}