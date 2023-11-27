package peaksoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "trainers")
@Getter
@Setter
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Specialization specialization;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToMany(mappedBy = "trainers", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Trainee> trainees;


}
