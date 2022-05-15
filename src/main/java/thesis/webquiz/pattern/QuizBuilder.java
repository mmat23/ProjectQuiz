package thesis.webquiz.pattern;

import java.util.List;

import thesis.webquiz.model.*;

public interface QuizBuilder {
    
    QuizBuilder title(String title);

    QuizBuilder description(String description);

    QuizBuilder user(QuizUser user);

    QuizBuilder questions(List<Question> questions);

    QuizBuilder comments(List<Comment> comments);

    QuizBuilder statistics(Statistics statistics);

    QuizBuilder subjects(List<Subject> subjects);

    Quiz build();

}
