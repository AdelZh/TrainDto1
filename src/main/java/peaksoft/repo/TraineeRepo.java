package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.dto.trainee.TraineeProfileRes;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;
import peaksoft.entity.Training;

import java.util.List;
import java.util.Optional;


@Repository
public interface TraineeRepo extends JpaRepository<Trainee, Long> {

    Optional<Trainee> findByUser_UserNameAndUser_Password(String username, String password);

    Optional<Trainee> getTraineeByUserUserName(@Param("userName") String username);

    @Query("SELECT t FROM Trainee t WHERE t.user.userName = :username")
    Optional<TraineeProfileRes> findByUser_UserName(@Param("username") String username);

    @Query("SELECT t FROM Trainee t JOIN t.user u WHERE u.userName = :username")
    Trainee findTraineeByUser_Username(@Param("username") String username);

    @Query("select t from Training t join t.trainee.user u where u.userName in :usernames")
    List<Training> findByTraining_UserNameIn(@Param("usernames") List<String> usernames);




}

