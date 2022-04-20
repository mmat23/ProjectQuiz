package thesis.webquiz.model;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Date date;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private QuizUser quizUser;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}

