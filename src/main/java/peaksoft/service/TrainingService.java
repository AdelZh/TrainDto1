package peaksoft.service;

import peaksoft.dto.trainer.FreeRequest;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.training.TrainingRequest;
import peaksoft.dto.user.SimpleResponse;
import peaksoft.entity.Trainer;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {

    SimpleResponse saveTraining(TrainingRequest trainingRequest);


    List<TrainerProfileRes2> getNotAssignedTrainers(FreeRequest freeRequest);
}
