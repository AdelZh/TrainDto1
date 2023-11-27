package peaksoft.dto.trainee;


import lombok.Getter;
import lombok.Setter;
import peaksoft.entity.Trainee;
import peaksoft.entity.Trainer;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TraineeResponse {

    private String username;
    private String password;



    public TraineeResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

}

