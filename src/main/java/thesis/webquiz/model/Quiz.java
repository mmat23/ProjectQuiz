package thesis.webquiz.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;
import thesis.webquiz.pattern.QuizBuilder;
import thesis.webquiz.pattern.QuizBuilderImpl;

@Setter
@Getter
@Entity
@Table(name = "quiz")
public class Quiz extends BaseModel {

    private String title;

    private String description;

    @Transient
    private List<Long> choosedOptions;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuizUser user;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<Question>();

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToOne(mappedBy = "quiz", cascade = CascadeType.ALL)
    private Statistics statistics;

    @ManyToMany(mappedBy = "quizzes")
    private List<Subject> subjects = new ArrayList<Subject>();

    public Quiz(){}

    public Quiz(String title, String description, QuizUser user, List<Question> questions, List<Comment> comments, Statistics statistics, List<Subject> subjects) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.questions = questions;
        this.comments = comments;
        this.statistics = statistics;
        this.subjects = subjects;
    }

    public static QuizBuilder builder() {
        return new QuizBuilderImpl();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((questions == null) ? 0 : questions.hashCode());
        result = prime * result + ((statistics == null) ? 0 : statistics.hashCode());
        result = prime * result + ((subjects == null) ? 0 : subjects.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quiz other = (Quiz) obj;
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (questions == null) {
            if (other.questions != null)
                return false;
        } else if (!questions.equals(other.questions))
            return false;
        if (statistics == null) {
            if (other.statistics != null)
                return false;
        } else if (!statistics.equals(other.statistics))
            return false;
        if (subjects == null) {
            if (other.subjects != null)
                return false;
        } else if (!subjects.equals(other.subjects))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    
}
