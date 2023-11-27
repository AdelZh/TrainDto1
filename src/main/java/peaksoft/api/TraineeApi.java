package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.trainee.*;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.user.SimpleResponse;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;
import peaksoft.entity.User;
import peaksoft.repo.TraineeRepo;
import peaksoft.service.TraineeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainee")
public class TraineeApi {

    private final TraineeService traineeService;
    private final TraineeRepo traineeRepo;




    @PostMapping("/create1")
    public TraineeResponse saveTrainee(@RequestBody TraineeRequest traineeRequest) {
        return traineeService.saveTrainee(traineeRequest);

    }

    @PutMapping("/change-password")
    public LoginChangeResponse changeTraineePassword(@RequestBody LoginChange loginChange) {
        String username = loginChange.getUsername();
        String oldPassword = loginChange.getOldPassword();
        String newPassword = loginChange.getNewPassword();

        Optional<Trainee> traineeOptional = traineeRepo.findByUser_UserNameAndUser_Password(username, oldPassword);

        if (traineeOptional.isPresent()) {
            Trainee trainee = traineeOptional.get();
            User user = trainee.getUser();
            user.setPassword(newPassword);
            traineeRepo.save(trainee);

            return new LoginChangeResponse(HttpStatus.OK, "Password changed successfully");
        } else {
            return new LoginChangeResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }


    @GetMapping("/profile")
    public TraineeProfileRes getTraineeProfile(@RequestParam String username) {
        return traineeService.getTraineeProfile(username);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TraineeProfileRes> updateTraineeProfile(
            @PathVariable Long id,
            @RequestBody UpdateRequest updateRequest) {
        TraineeProfileRes updatedProfile = traineeService.update(id, updateRequest);
        return ResponseEntity.ok(updatedProfile);
    }


    @DeleteMapping("/delete")
    public SimpleResponse delete(@RequestParam String userName) {
        return traineeService.delete(userName);
    }


    @GetMapping("/not-assigned")
    public TrainerProfileRes2 getNotAssignedTrainer(@RequestParam String username) {
        return traineeService.getNotAssignedTrainer(username);
    }


    @PatchMapping("/activate-deactivate-trainee/{id}")
    public ResponseEntity<SimpleResponse> activateDeactivateTrainee(@RequestBody ActivateRequest activateRequest, @PathVariable Long id) {
        SimpleResponse response = traineeService.activateDeactivateTrainee(id,activateRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{traineeId}/update-trainers")
    public ResponseEntity<Update2Response> updateTrainersList(
            @PathVariable Long traineeId,
            @RequestBody @Valid UpdateRequest2 updateRequest
    ) {
        Update2Response response = traineeService.updateTrainersList(traineeId, updateRequest);
        return ResponseEntity.ok(response);
    }
}








