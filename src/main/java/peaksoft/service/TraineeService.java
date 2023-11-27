package peaksoft.service;

import org.aspectj.weaver.SimpleAnnotationValue;
import peaksoft.dto.trainee.*;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.user.SimpleResponse;

import java.util.List;


public interface TraineeService {


    TraineeResponse saveTrainee(TraineeRequest traineeRequest);
    TraineeProfileRes getTraineeProfile(String username);
    TraineeProfileRes update(Long id, UpdateRequest updateRequest);

    SimpleResponse delete(String username);
    SimpleResponse activateDeactivateTrainee(Long Id, ActivateRequest activateRequest);

    TrainerProfileRes2 getNotAssignedTrainer(String username);
    Update2Response updateTrainersList(Long traineeId, UpdateRequest2 updateRequest);


}
