package peaksoft.dto.trainee;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;
import peaksoft.entity.Specialization;

@Getter
@Setter
public class TrainerInfo {



        private String firstName;
        private String lastName;
        private String userName;
        private Specialization specialization;

        public TrainerInfo(String firstName, String lastName, String userName, Specialization specialization) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.userName = userName;
            this.specialization = specialization;
        }
    }

