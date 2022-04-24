package thesis.webquiz.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Boolean correct = false;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
