package peaksoft.dto.trainee;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class UpdateRequest {


    private String address;
    private Date dateOfBirth;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean isActive;
}
