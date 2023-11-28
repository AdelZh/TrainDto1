package peaksoft.dto.trainer;

import lombok.Getter;
import lombok.Setter;
import peaksoft.entity.Specialization;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;
import peaksoft.entity.User;

import java.util.List;

@Getter
@Setter
public class TrainerProfileRes {

    private String userName;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private Specialization specialization;
    private List<Trainee> trainees;


    public TrainerProfileRes(User user, Trainer trainer) {
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.isActive=user.isActive();
        this.specialization=trainer.getSpecialization();
        this.trainees=trainer.getTrainees();
    }


    public TrainerProfileRes(String userName, String firstName, String lastName, boolean active, List<Trainee> trainees, Specialization specialization) {
        this.userName=userName;
        this.firstName=firstName;
        this.lastName=lastName;
        this.isActive=active;
        this.trainees=trainees;
        this.specialization=specialization;
    }

    public TrainerProfileRes(String firstName, String lastName, boolean active, Specialization specialization, List<Trainee> trainees) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.isActive=active;
        this.specialization=specialization;
        this.trainees=trainees;
    }
}
