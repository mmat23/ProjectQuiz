package thesis.webquiz.pattern;

import java.util.List;

import thesis.webquiz.model.Question;

public class QuestionCollection {
    
    private List<Question> questions;

    public QuestionCollection(List<Question> questions) {
        this.questions = questions;
    }

    public Iterator<Question> iterator(){
        return new QuestionIterator();
    }

    private class QuestionIterator implements Iterator<Question>{

        private int index = 0;

        public Question next(){
            Question obj = questions.get(index);
            index++;
            return obj;
        }

        public boolean hasNext(){
            return index < questions.size();
        }
    }

}
