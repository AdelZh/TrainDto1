package peaksoft.api;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import peaksoft.dto.trainee.ActivateRequest;
import peaksoft.dto.trainee.TraineeProfileRes;
import peaksoft.dto.trainee.UpdateRequest;
import peaksoft.dto.trainer.TrainerProfileRes;
import peaksoft.dto.trainer.TrainerProfileRes2;
import peaksoft.dto.trainer.TrainerRequest;
import peaksoft.dto.trainer.TrainerResponse;
import peaksoft.dto.user.SimpleResponse;
import peaksoft.service.TrainerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerApi {

    private final TrainerService trainerService;


    @PostMapping("/create")
    public TrainerResponse saveTrainer(@RequestBody TrainerRequest trainerRequest) {
        return trainerService.saveTrainer(trainerRequest);
    }


    @GetMapping("/profile")
    public TrainerProfileRes getTrainerProfile(@RequestParam String username) {
        return trainerService.getTrainerProfile(username);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TrainerProfileRes> updateTrainerProfile(
            @PathVariable Long id,
            @RequestBody UpdateRequest updateRequest) {
        TrainerProfileRes updatedProfile = trainerService.update(id, updateRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    @PatchMapping("/activate-deactivate-trainer/{id}")
    public ResponseEntity<SimpleResponse> activateDeactivateTrainee(@RequestBody ActivateRequest activateRequest, @PathVariable Long id) {
        SimpleResponse response = trainerService.activateDeactivateTrainer(id,activateRequest);
        return ResponseEntity.ok(response);
    }
}



