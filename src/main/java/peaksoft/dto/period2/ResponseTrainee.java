package peaksoft.dto.period2;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResponseTrainee {

    private String trainingName;
    private LocalDate trainingDate;
    private int Duration;
    private String traineeName;

    public ResponseTrainee(String trainingName, LocalDate trainingDate, int duration, String traineeName) {
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        Duration = duration;
        this.traineeName = traineeName;

}
}
