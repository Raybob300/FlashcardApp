package flashcardapp;

import java.util.*;

public class FlashcardApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlashcardSet set = new FlashcardSet();

        while (true) {
            System.out.println("\n--- Flashcard App ---");
            System.out.println("1. Add Flashcard");
            System.out.println("2. Quiz Yourself");
            System.out.println("3. View All Cards");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.println("Choose type of flashcard:");
                    System.out.println("1. Text");
                    System.out.println("2. Multiple Choice");
                    String type = scanner.nextLine();

                    Flashcard card;

                    if (type.equals("2")) {
                        System.out.print("Enter question: ");
                        String question = scanner.nextLine();
                        System.out.print("Enter correct answer: ");
                        String correct = scanner.nextLine();

                        List<String> options = new ArrayList<>();
                        options.add(correct);
                        System.out.println("Enter 3 more choices:");
                        for (int i = 0; i < 3; i++) {
                            options.add(scanner.nextLine());
                        }
                        Collections.shuffle(options);
                        card = new MultipleChoiceFlashcard(question, correct, options);
                    } else {
                        System.out.print("Enter question: ");
                        String question = scanner.nextLine();
                        System.out.print("Enter answer: ");
                        String answer = scanner.nextLine();
                        card = new Flashcard(question, answer);
                    }

                    set.addCard(card);
                    System.out.println("Flashcard added.");
                }

                case "2" -> {
                    if (set.size() == 0) {
                        System.out.println("No cards yet.");
                        break;
                    }
                    for (int i = 0; i < set.size(); i++) {
                        Flashcard card = set.getCard(i);
                        System.out.println("\nQ: " + card.getQuestion());

                        if (card instanceof MultipleChoiceFlashcard mcq) {
                            List<String> opts = mcq.getOptions();
                            for (int j = 0; j < opts.size(); j++) {
                                System.out.println((char) ('A' + j) + ". " + opts.get(j));
                            }
                            System.out.print("Your answer (A-D): ");
                            String input = scanner.nextLine().toUpperCase();
                            int index = input.charAt(0) - 'A';
                            if (index >= 0 && index < opts.size()) {
                                if (opts.get(index).equalsIgnoreCase(mcq.getAnswer())) {
                                    System.out.println("Correct!");
                                } else {
                                    System.out.println("Wrong. Correct: " + mcq.getAnswer());
                                }
                            } else {
                                System.out.println("Invalid option.");
                            }
                        } else {
                            System.out.print("Your answer: ");
                            String response = scanner.nextLine();
                            if (response.equalsIgnoreCase(card.getAnswer())) {
                                System.out.println("Correct!");
                            } else {
                                System.out.println("Wrong. Correct: " + card.getAnswer());
                            }
                        }
                    }
                }

                case "3" -> {
                    System.out.println("All Cards:");
                    set.listCards();
                }

                case "4" -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
