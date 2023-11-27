package peaksoft.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import peaksoft.entity.Trainee;
import peaksoft.entity.User;

@Getter
@Setter
@AllArgsConstructor
public class SimpleResponse {


    private HttpStatus httpStatus;
    private String message;


}
