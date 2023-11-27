package peaksoft.dto.trainee;


import lombok.Getter;
import lombok.Setter;
import peaksoft.entity.Specialization;
import peaksoft.entity.Trainer;
import peaksoft.repo.TrainerRepo;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Update2Response {


    private List<TrainerInfo> trainersInfo;


    public Update2Response(String firstName, String lastName, String userName, Specialization specialization) {
        this.trainersInfo = List.of(new TrainerInfo(firstName, lastName, userName, specialization));
    }

    public Update2Response(List<Update2Response> updateResponses) {
        this.trainersInfo = updateResponses.stream()
                .flatMap(updateResponse -> updateResponse.getTrainersInfo().stream())
                .collect(Collectors.toList());
    }
}

