package thesis.webquiz.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Transient
    private List<Long> choosedOptions;

    @Transient
    private Integer quesCount;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<Question>();

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToOne(mappedBy = "quiz")
    private Statistics statistics;

    @ManyToMany(mappedBy = "quizzes")
    private List<Subject> subjects = new ArrayList<Subject>();
}
