package peaksoft.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.period.PeriodTrainingsList;
import peaksoft.dto.period.ResponseTrainers;
import peaksoft.dto.trainee.*;
import peaksoft.dto.user.SimpleResponse;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;
import peaksoft.entity.Training;
import peaksoft.entity.User;
import peaksoft.repo.TraineeRepo;
import peaksoft.repo.TrainerRepo;
import peaksoft.service.TraineeService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepo traineeRepo;
    private final TrainerRepo trainerRepo;


    @Override
    public TraineeResponse saveTrainee(TraineeRequest traineeRequest) {
        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(traineeRequest.getDateOfBirth());
        trainee.setAddress(traineeRequest.getAddress());


        User user = new User();
        user.setFirstName(traineeRequest.getFirstName());
        user.setLastName(traineeRequest.getLastName());
        user.setActive(true);


        String username = traineeRequest.getFirstName().toLowerCase() +
                traineeRequest.getLastName().toLowerCase();
        user.setUserName(username);
        user.setPassword(username);


        user.setActive(true);
        trainee.setUser(user);
        traineeRepo.save(trainee);
        return new TraineeResponse(user.getUserName(), user.getPassword());

    }

    @Override
    public TraineeProfileRes getTraineeProfile(ActivateRequest activateRequest) {
        Trainee trainee = traineeRepo.findTraineeByUser_Username(activateRequest.getUserName());

        User user = trainee.getUser();

        return new TraineeProfileRes(
                user.getFirstName(),
                user.getLastName(),
                user.isActive(),
                trainee.getAddress(),
                trainee.getDateOfBirth(),
                trainee.getTrainers()
        );
    }


    @Override
    public TraineeProfileRes update(UpdateRequest updateRequest) {
        Trainee trainee = traineeRepo.findTraineeByUser_Username(updateRequest.getUserName());

        trainee.setDateOfBirth(updateRequest.getDateOfBirth());
        trainee.setAddress(updateRequest.getAddress());


        User user = trainee.getUser();
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setActive(true);

        traineeRepo.save(trainee);


        return new TraineeProfileRes(
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName(),
                trainee.getUser().isActive(),
                trainee.getAddress(),
                trainee.getDateOfBirth(),
                trainee.getTrainers());
    }

    @Override
    public SimpleResponse delete(ActivateRequest activateRequest) {
        Trainee trainee = traineeRepo.findTraineeByUser_Username(activateRequest.getUserName());
        traineeRepo.delete(trainee);
        return new SimpleResponse(HttpStatus.OK, "Trainee deleted successfully");

    }


    @Override
    public SimpleResponse activateDeactivateTrainee(ActivateRequest activateRequest) {
        Trainee trainee = traineeRepo.findTraineeByUser_Username(activateRequest.getUserName());
        trainee.getUser().setActive(activateRequest.getIsActive());
        traineeRepo.save(trainee);
        return new SimpleResponse(HttpStatus.OK, "200 ok");
    }


    @Override
    public Update2Response updateTrainersList(UpdateRequest2 updateRequest) {
        Trainee trainee = traineeRepo.findTraineeByUser_Username(updateRequest.getTraineeUsername());


        List<String> trainersUsername = updateRequest.getUsernames();
        List<Trainer> trainers = trainerRepo.findByUser_UserNameIn(trainersUsername);
        List<Trainer> exist = trainee.getTrainers();
        trainee.setTrainers(trainers);


        exist.addAll(trainers);
        trainee.setTrainers(exist);
        traineeRepo.save(trainee);

        List<Update2Response> updateResponses = trainers.stream()
                .map(trainer -> new Update2Response(
                        trainer.getUser().getFirstName(),
                        trainer.getUser().getLastName(),
                        trainer.getUser().getUserName(),
                        trainer.getSpecialization()
                ))
                .collect(Collectors.toList());

        return new Update2Response(updateResponses);
    }




    @Override
    public List<ResponseTrainers> getTrainings(PeriodTrainingsList periodTrainingsList) {
        Trainee trainee = traineeRepo.findTraineeByUser_Username(periodTrainingsList.getUserName());

        List<String> trainings1 = Collections.singletonList(periodTrainingsList.getUserName());
        List<Training> trainings = traineeRepo.findByTraining_UserNameIn(trainings1);

        LocalDate periodFrom = periodTrainingsList.getPeriodFrom();
        LocalDate periodTo = periodTrainingsList.getPeriodTo();

        long durationInDays = ChronoUnit.DAYS.between(periodFrom, periodTo);


        return trainings.stream()
                .map(training -> new ResponseTrainers(
                        training.getTrainingName(),
                        training.getTrainingDate(),
                        (int) durationInDays,
                        training.getTrainer().getUser().getFirstName()
                ))
                .collect(Collectors.toList());
    }
}


