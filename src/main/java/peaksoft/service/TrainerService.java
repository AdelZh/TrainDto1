package peaksoft.service;

import peaksoft.dto.period.PeriodTrainingsList;
import peaksoft.dto.period.ResponseTrainers;
import peaksoft.dto.period2.ResponseTrainee;
import peaksoft.dto.trainee.ActivateRequest;
import peaksoft.dto.trainee.UpdateRequest;
import peaksoft.dto.trainer.TrainerProfileRes;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.trainer.TrainerRequest;
import peaksoft.dto.trainer.TrainerResponse;
import peaksoft.dto.user.SimpleResponse;

import java.util.List;

public interface TrainerService {

    TrainerResponse saveTrainer(TrainerRequest trainerRequest);
     TrainerProfileRes getTrainerProfile(UpdateRequest updateRequest);

     TrainerProfileRes update( UpdateRequest updateRequest);

    SimpleResponse activateDeactivateTrainer(ActivateRequest activateRequest);
    List<ResponseTrainee> getTrainings(PeriodTrainingsList periodTrainingsList);


}
