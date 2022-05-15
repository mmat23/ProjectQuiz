package thesis.webquiz.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import thesis.webquiz.pattern.*;


@Setter
@Getter
@Entity
@Table(name = "question")
public class Question extends BaseModel {

    private String text;

    @Transient
    private Integer correctAnsIndex;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<Answer>();

    public static Iterator<Question> iterator(List<Question> questions){
        return new QuestionCollection(questions).iterator();
    }
}
