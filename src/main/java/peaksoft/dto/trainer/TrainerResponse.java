package peaksoft.dto.trainer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerResponse {

    private String username;
    private String password;
    private String specialization;


    public TrainerResponse(String username, String password) {
        this.username = username;
        this.password = password;

    }
}



