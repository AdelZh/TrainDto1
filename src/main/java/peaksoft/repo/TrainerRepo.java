package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.dto.trainee.TraineeProfileRes;
import peaksoft.dto.trainer.TrainerProfileRes;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainerRepo extends JpaRepository<Trainer, Long> {



    @Query("SELECT new peaksoft.dto.trainer.TrainerProfileRes(t.user, t) FROM Trainer t WHERE t.user.userName = :username")
    Optional<TrainerProfileRes> findByUser_UserName(@Param("username") String username);

    Optional<Trainer> getTrainerByUserUserName(@Param("userName") String username);



    @Query("SELECT t FROM Trainer t WHERE t.id = :id")
    Optional<Trainer> getTraineeById(Long id);



   /* @Query("SELECT t FROM Trainer t " +
            "WHERE NOT EXISTS (SELECT 1 FROM t.trainees trainee WHERE trainee.id = :traineeId) " +
            "AND t.user.isActive = true")
   Trainer findNotAssignedTrainers(@Param("traineeId") Long traineeId);

    */
   @Query("SELECT t FROM Trainer t WHERE t.user.isActive = true")
   List<Trainer> findActiveTrainer();

    @Query("select t from Trainer t join t.user u where u.userName in :usernames")
    List<Trainer> findByUser_UserNameIn(@Param("usernames") List<String> usernames);


}


