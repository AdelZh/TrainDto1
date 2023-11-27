package peaksoft.service;

import peaksoft.dto.trainee.ActivateRequest;
import peaksoft.dto.trainee.UpdateRequest;
import peaksoft.dto.trainer.TrainerProfileRes;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.trainer.TrainerRequest;
import peaksoft.dto.trainer.TrainerResponse;
import peaksoft.dto.user.SimpleResponse;

public interface TrainerService {

    TrainerResponse saveTrainer(TrainerRequest trainerRequest);
    TrainerProfileRes getTrainerProfile(String username);

    TrainerProfileRes update(Long id, UpdateRequest updateRequest);

    SimpleResponse activateDeactivateTrainer(Long Id, ActivateRequest activateRequest);


}
