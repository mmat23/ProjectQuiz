package thesis.webquiz.model;

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

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToOne(mappedBy = "quiz")
    private Statistics statistics;

    @ManyToMany(mappedBy = "quizzes")
    private List<Subject> subjects;
}
