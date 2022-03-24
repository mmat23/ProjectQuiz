package tesis.webquiz.model;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "quiz")
public class Quiz{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subject;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private Set<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private Set<Comment> comments;
}
