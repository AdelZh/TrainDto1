package peaksoft.dto.trainee;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LoginChangeResponse {

    private HttpStatus httpStatus;
    private String message;

    public LoginChangeResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
