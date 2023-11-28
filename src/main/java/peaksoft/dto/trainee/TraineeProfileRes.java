package peaksoft.dto.trainee;

import lombok.Getter;
import lombok.Setter;
import peaksoft.entity.Specialization;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;
import peaksoft.entity.User;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class TraineeProfileRes extends Trainee {

    private String firstName;
    private String lastName;
    private boolean isActive;
    private String address;
    private Date dateOfBirth;
    private List<Trainer> trainers;

    public TraineeProfileRes(User user, Trainee trainee) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.isActive = user.isActive();
        this.address = trainee.getAddress();
        this.dateOfBirth = trainee.getDateOfBirth();
        this.trainers = trainee.getTrainers();
    }

    public TraineeProfileRes(String firstName, String lastName, boolean isActive, String address, Date dateOfBirth, List<Trainer> trainers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.trainers = trainers;
    }

}