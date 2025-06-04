package flashcardapp;

import java.util.ArrayList;

public class FlashcardSet {
    private ArrayList<Flashcard> cards = new ArrayList<>();

    public void addCard(Flashcard card) {
        cards.add(card);
    }

    public Flashcard getCard(int index) {
        return cards.get(index);
    }

    public int size() {
        return cards.size();
    }

    public ArrayList<Flashcard> getAllCards() {
        return cards;
    }

	public void listCards() {
		// TODO Auto-generated method stub
		
	}
}
