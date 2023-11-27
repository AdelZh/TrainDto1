package peaksoft.dto.trainer;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.entity.Specialization;
import peaksoft.entity.Trainer;
import peaksoft.entity.User;

@Getter
@Setter
@NoArgsConstructor
public class TrainerProfileRes2 {
    private String userName;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private Specialization specialization;

    public TrainerProfileRes2(User user, Trainer trainer) {
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.isActive = user.isActive();
        this.specialization = trainer.getSpecialization();
    }



    public TrainerProfileRes2(String userName, String firstName, String lastName, Specialization specialization) {
        this.userName=userName;
        this.firstName=firstName;
        this.lastName=lastName;
       this.specialization=specialization;

    }
}

