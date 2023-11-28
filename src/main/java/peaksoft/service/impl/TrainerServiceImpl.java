package peaksoft.service.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.period.PeriodTrainingsList;
import peaksoft.dto.period.ResponseTrainers;
import peaksoft.dto.period2.ResponseTrainee;
import peaksoft.dto.trainee.ActivateRequest;
import peaksoft.dto.trainee.TraineeProfileRes;
import peaksoft.dto.trainee.Update2Response;
import peaksoft.dto.trainee.UpdateRequest;
import peaksoft.dto.trainer.TrainerProfileRes;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.trainer.TrainerRequest;
import peaksoft.dto.trainer.TrainerResponse;
import peaksoft.dto.user.SimpleResponse;
import peaksoft.entity.*;
import peaksoft.repo.TraineeRepo;
import peaksoft.repo.TrainerRepo;
import peaksoft.service.TrainerService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {


    private final TrainerRepo trainerRepo;


    @Override
    public TrainerResponse saveTrainer(TrainerRequest trainerRequest) {
        Trainer trainee = new Trainer();


        Specialization specialization = new Specialization();
        specialization.setSpecializationType(trainerRequest.getSpecialization());

        User user = new User();
        user.setFirstName(trainerRequest.getFirstName());
        user.setLastName(trainerRequest.getLastName());


        String username = trainerRequest.getFirstName().toLowerCase() +
                trainerRequest.getLastName().toLowerCase();
        user.setUserName(username);
        user.setPassword(username);

        user.setActive(true);

        trainee.setUser(user);
        trainee.setSpecialization(specialization);

        trainerRepo.save(trainee);


        return new TrainerResponse(user.getUserName(), user.getPassword());
    }


    @Override
    public TrainerProfileRes getTrainerProfile(UpdateRequest updateRequest) {
        Trainer trainer = trainerRepo.findTraineeByUser_Username(updateRequest.getUserName());

        User user = trainer.getUser();

        return new TrainerProfileRes(
                user.getFirstName(),
                user.getLastName(),
                user.isActive(),
                trainer.getSpecialization(),
                trainer.getTrainees()
        );
    }


    @Override
    public TrainerProfileRes update(UpdateRequest updateRequest) {
        Trainer trainer = trainerRepo.findTraineeByUser_Username(updateRequest.getUserName());
        User user = trainer.getUser();
        user.setUserName(updateRequest.getUserName());
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setActive(user.isActive());
        trainerRepo.save(trainer);

        return new TrainerProfileRes(
                trainer.getUser().getUserName(),
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                trainer.getUser().isActive(),
                trainer.getTrainees(),
                trainer.getSpecialization());
    }

    @Override
    public SimpleResponse activateDeactivateTrainer(ActivateRequest activateRequest) {
        Trainer trainer = trainerRepo.findTraineeByUser_Username(activateRequest.getUserName());

        trainer.getUser().setActive(activateRequest.getIsActive());
        trainerRepo.save(trainer);
        return new SimpleResponse(HttpStatus.OK, "200 ok");
    }


    @Override
    public List<ResponseTrainee> getTrainings(PeriodTrainingsList periodTrainingsList){
        Trainer trainer = trainerRepo.findTraineeByUser_Username(periodTrainingsList.getUserName());

        List<String> trainings1 = Collections.singletonList(periodTrainingsList.getUserName());
        List<Training> trainings = trainerRepo.findByTraining_UserNameIn(trainings1);

        LocalDate periodFrom = periodTrainingsList.getPeriodFrom();
        LocalDate periodTo = periodTrainingsList.getPeriodTo();

        long durationInDays = ChronoUnit.DAYS.between(periodFrom, periodTo);


        return trainings.stream()
                .map(training -> new ResponseTrainee(
                        training.getTrainingName(),
                        training.getTrainingDate(),
                        (int) durationInDays,
                        training.getTrainer().getUser().getFirstName()
                ))
                .collect(Collectors.toList());
    }
}
