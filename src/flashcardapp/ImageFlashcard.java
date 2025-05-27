package flashcardapp;

import java.io.File;

public class ImageFlashcard extends Flashcard {
    private String imagePath;

    public ImageFlashcard(String question, String answer, String imageFileName) {
        super(question, answer);
        File file = new File("images/" + imageFileName);
        if (!file.exists()) {
            throw new IllegalArgumentException("Image not found in /images/: " + imageFileName);
        }
        this.imagePath = file.getPath();
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String getType() {
        return "Image Flashcard";
    }

    @Override
    public String toString() {
        return "[Image] Q: " + question + " | A: " + answer + " | Image: " + imagePath;
    }
}
