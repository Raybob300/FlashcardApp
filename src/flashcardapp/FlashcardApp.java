package flashcardapp;

import java.util.Scanner;

public class FlashcardApp {
    public static void main(String[] args) {
        FlashcardSet set = new FlashcardSet();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add flashcard");
            System.out.println("2. Start quiz");
            System.out.println("3. Show all flashcards");
            System.out.println("4. Exit");
            System.out.print(">> ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter question: ");
                    String question = scanner.nextLine();
                    System.out.print("Enter answer: ");
                    String answer = scanner.nextLine();
                    System.out.print("Enter image file name (in /images/, or leave blank): ");
                    String imageFile = scanner.nextLine();

                    Flashcard card;
                    try {
                        if (!imageFile.isEmpty()) {
                            card = new ImageFlashcard(question, answer, imageFile);
                        } else {
                            card = new Flashcard(question, answer);
                        }
                        set.addCard(card);
                        System.out.println("Flashcard added.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                    if (set.size() == 0) {
                        System.out.println("No flashcards available.");
                        break;
                    }

                    for (int i = 0; i < set.size(); i++) {
                        Flashcard f = set.getCard(i);
                        System.out.println("Q: " + f.getQuestion());
                        System.out.print("Your answer: ");
                        String response = scanner.nextLine();

                        if (response.trim().equalsIgnoreCase(f.getAnswer().trim())) {
                            System.out.println("Correct.");
                        } else {
                            System.out.println("Incorrect. Correct answer: " + f.getAnswer());
                        }
                    }
                    break;

                case "3":
                    if (set.size() == 0) {
                        System.out.println("No flashcards to show.");
                    } else {
                        for (Flashcard f : set.getAllCards()) {
                            System.out.println(f);
                        }
                    }
                    break;

                case "4":
                    running = false;
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}
