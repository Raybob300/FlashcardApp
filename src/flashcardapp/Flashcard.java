package flashcardapp;

public class Flashcard {
    private String question;
    private String answer;
    private String imageName; // can be null

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.imageName = imageName;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getImageName() {
        return imageName;
    }

    @Override
    public String toString() {
        String output = "Q: " + question + "\nA: " + answer;
        if (imageName != null && !imageName.isEmpty()) {
            output += "\n[Image: " + imageName + "]";
        }
        return output;
    }
}
