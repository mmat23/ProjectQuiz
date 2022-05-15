package thesis.webquiz.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "statistics")
public class Statistics extends BaseModel {

    private Long countPlayed = (long) 1;
    
    private Double avgResult = 1.0;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}