package peaksoft.dto.trainee;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginChange {

    private String username;
    private String oldPassword;
    private String newPassword;

}
