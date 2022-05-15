package thesis.webquiz.pattern;

import java.util.List;

import thesis.webquiz.model.*;

public class QuizBuilderImpl implements QuizBuilder {
    private String title;

    private String description;

    private QuizUser user;

    private List<Question> questions;

    private List<Comment> comments;

    private Statistics statistics;

    private List<Subject> subjects;

    @Override
    public QuizBuilder title(String title){
        this.title = title;
        return this;
    }

    @Override
    public QuizBuilder description(String description){
        this.description = description;
        return this;
    }

    @Override
    public QuizBuilder user(QuizUser user){
        this.user = user;
        return this;
    }

    @Override
    public QuizBuilder questions(List<Question> questions){
        this.questions = questions;
        return this;
    }

    @Override
    public QuizBuilder comments(List<Comment> comments){
        this.comments = comments;
        return this;
    }

    @Override
    public QuizBuilder statistics(Statistics statistics){
        this.statistics = statistics;
        return this;
    }

    @Override
    public QuizBuilder subjects(List<Subject> subjects){
        this.subjects = subjects;
        return this;
    }
    
    public Quiz build(){
        return new Quiz(title, description, user, questions, comments, statistics, subjects);
    }
}