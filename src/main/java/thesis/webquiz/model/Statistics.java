package thesis.webquiz.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long countPlayed = (long) 0;
    
    private Double avgResult = 0.0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}