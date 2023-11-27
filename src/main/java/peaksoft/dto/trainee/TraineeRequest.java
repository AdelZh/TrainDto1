package peaksoft.dto.trainee;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TraineeRequest {

    private String address;
    private Date dateOfBirth;
    private String firstName;
    private String lastName;

}
