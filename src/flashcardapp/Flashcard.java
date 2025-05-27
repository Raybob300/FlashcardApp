package flashcardapp;

public class Flashcard {
    protected String question;
    protected String answer;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getType() {
        return "Text Flashcard";
    }

    @Override
    public String toString() {
        return "[Text] Q: " + question + " | A: " + answer;
    }
}
