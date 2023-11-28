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
import peaksoft.entity.Training;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainerRepo extends JpaRepository<Trainer, Long> {



    @Query("SELECT new peaksoft.dto.trainer.TrainerProfileRes(t.user, t) FROM Trainer t WHERE t.user.userName = :username")
    Optional<TrainerProfileRes> findByUser_UserName(@Param("username") String username);

    Optional<Trainer> getTrainerByUserUserName(@Param("userName") String username);

    @Query("SELECT t FROM Trainer t WHERE t.id = :id")
    Optional<Trainer> getTraineeById(Long id);

    @Query("SELECT t FROM Trainer t WHERE t.user.isActive = true")
    List<Trainer> findActiveTrainer();

    @Query("select t from Trainer t join t.user u where u.userName in :usernames")
    List<Trainer> findByUser_UserNameIn(@Param("usernames") List<String> usernames);

    @Query("SELECT t FROM Trainer t JOIN t.user u WHERE u.userName = :username")
    Trainer findTraineeByUser_Username(@Param("username") String username);


    @Query("select t from Training t join t.trainer.user u where u.userName in :usernames")
    List<Training> findByTraining_UserNameIn(@Param("usernames") List<String> usernames);



}


