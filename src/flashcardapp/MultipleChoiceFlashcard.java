package flashcardapp;

import java.util.List;

public class MultipleChoiceFlashcard extends Flashcard {
    private List<String> choices;

    public MultipleChoiceFlashcard(String question, String answer, List<String> choices) {
        super(question, answer);
        this.choices = choices;
    }

    public List<String> getChoices() {
        return choices;
    }

    @Override
    public String toString() {
        return super.toString() + " | Choices: " + String.join(", ", choices);
    }

	public List<String> getOptions() {
		// TODO Auto-generated method stub
		return null;
	}
}
