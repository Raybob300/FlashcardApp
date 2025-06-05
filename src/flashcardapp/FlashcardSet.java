package flashcardapp;

import java.util.ArrayList;
import java.util.List;

public class FlashcardSet {
    private List<Flashcard> cards;

    public FlashcardSet() {
        cards = new ArrayList<>();
    }

    public void addCard(Flashcard card) {
        cards.add(card);
    }

    public Flashcard getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        }
        return null;
    }

    public List<Flashcard> getAllCards() {
        return cards;
    }

    public int size() {
        return cards.size();
    }

	public void listCards() {
		// TODO Auto-generated method stub
		
	}
}
