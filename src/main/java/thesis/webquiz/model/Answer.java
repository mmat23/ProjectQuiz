package thesis.webquiz.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "answer")
public class Answer extends BaseModel {

    private String text;

    private Boolean correct = false;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
