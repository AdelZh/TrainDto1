package peaksoft.service.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.trainee.ActivateRequest;
import peaksoft.dto.trainee.UpdateRequest;
import peaksoft.dto.trainer.TrainerProfileRes;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.trainer.TrainerRequest;
import peaksoft.dto.trainer.TrainerResponse;
import peaksoft.dto.user.SimpleResponse;
import peaksoft.entity.Specialization;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;
import peaksoft.entity.User;
import peaksoft.repo.TraineeRepo;
import peaksoft.repo.TrainerRepo;
import peaksoft.service.TrainerService;

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
    public TrainerProfileRes getTrainerProfile(String username) {
        return trainerRepo.findByUser_UserName(username).orElseThrow(EntityNotFoundException::new);

    }


    @Override
    public TrainerProfileRes update(Long id, UpdateRequest updateRequest) {
        Trainer trainer = trainerRepo.findById(id).orElseThrow(EntityNotFoundException::new);
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
    public SimpleResponse activateDeactivateTrainer(Long Id, ActivateRequest activateRequest) {
        Trainer trainee = trainerRepo.getTraineeById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with id: " + Id));

        trainee.getUser().setActive(activateRequest.getIsActive());
        trainerRepo.save(trainee);
        return new SimpleResponse(HttpStatus.OK, "200 ok");
    }



}
