package peaksoft.service.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.trainee.*;
import peaksoft.dto.trainer.TrainerProfileRes;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.user.SimpleResponse;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;
import peaksoft.entity.User;
import peaksoft.repo.TraineeRepo;
import peaksoft.repo.TrainerRepo;
import peaksoft.service.TraineeService;

import java.util.ArrayList;
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
    public TraineeProfileRes getTraineeProfile(String username) {
        Trainee trainee=traineeRepo.getTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
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
    public TraineeProfileRes update(Long id, UpdateRequest updateRequest) {
        Trainee trainee = traineeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with id: " + id));

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
    public SimpleResponse delete(String username) {
        Trainee trainee = traineeRepo.getTraineeByUserUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with username: " + username));

        traineeRepo.delete(trainee);
        return new SimpleResponse(HttpStatus.OK, "Trainee deleted successfully");

    }


    @Override
    public SimpleResponse activateDeactivateTrainee(Long Id, ActivateRequest activateRequest) {
        Trainee trainee = traineeRepo.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with id: " + Id));

        trainee.getUser().setActive(activateRequest.getIsActive());
        traineeRepo.save(trainee);
        return new SimpleResponse(HttpStatus.OK, "200 ok");
    }



    @Override
    public TrainerProfileRes2 getNotAssignedTrainer(String username) {
        return null;
    }


    @Override
    public Update2Response updateTrainersList(Long traineeId, UpdateRequest2 updateRequest) {
        Trainee trainee = traineeRepo.findById(traineeId)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with ID: " + traineeId));

        List<String> trainersUsername = updateRequest.getUsernames();
        List<Trainer> trainers = trainerRepo.findByUser_UserNameIn(trainersUsername);
        trainee.setTrainers(trainers);
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
}


