package peaksoft.dto.trainer;

import lombok.Getter;
import lombok.Setter;
import peaksoft.entity.Specialization;

@Getter
@Setter
public class UpdateRequest {

    private String firstName;
    private String lastName;
    private String userName;
    private Boolean isActive;
    private Specialization specialization;
}
