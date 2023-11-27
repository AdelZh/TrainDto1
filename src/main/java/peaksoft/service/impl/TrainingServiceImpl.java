package peaksoft.service.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.trainer.FreeRequest;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.training.TrainingRequest;
import peaksoft.dto.user.SimpleResponse;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;
import peaksoft.entity.Training;
import peaksoft.entity.User;
import peaksoft.repo.TraineeRepo;
import peaksoft.repo.TrainerRepo;
import peaksoft.repo.TrainingRepo;
import peaksoft.service.TrainingService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepo trainingRepo;
    private final TrainerRepo trainerRepo;
    private final TraineeRepo traineeRepo;


    @Override
    public SimpleResponse saveTraining(TrainingRequest trainingRequest) {
        Trainee trainee = traineeRepo.getTraineeByUserUserName(trainingRequest.getTraineeUsername())
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with username: " + trainingRequest.getTraineeUsername()));

        Trainer trainer = trainerRepo.getTrainerByUserUserName(trainingRequest.getTrainerUsername())
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found with username: " + trainingRequest.getTrainerUsername()));


        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName(trainingRequest.getTrainingName());
        training.setTrainingDate(trainingRequest.getTrainingDate());
        training.setDuration(trainingRequest.getDuration());

        trainee.getTrainers().add(trainer);
        trainer.getTrainees().add(trainee);

        trainingRepo.save(training);

        return null;
    }

    @Override
    public List<TrainerProfileRes2> getNotAssignedTrainers(FreeRequest freeRequest) {
        String traineeUsername = freeRequest.getUserName();
        Trainee trainee = traineeRepo.findTraineeByUser_Username(traineeUsername);
        List<Trainer> trainers = trainerRepo.findActiveTrainer();


        List<Trainer> assignedTrainers = trainee.getTrainers();

        List<Trainer> notAssigned = trainers.stream()
                .filter(trainer -> !assignedTrainers.contains(trainer))
                .collect(Collectors.toList());

        return notAssigned.stream().map(
                trainer -> new TrainerProfileRes2(
                        trainer.getUser().getUserName(),
                        trainer.getUser().getFirstName(),
                        trainer.getUser().getLastName(),
                        trainer.getSpecialization()
                )
        ).collect(Collectors.toList());
    }
}


