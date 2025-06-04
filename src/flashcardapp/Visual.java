package flashcardapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class Visual {
    private FlashcardSet flashcardSet = new FlashcardSet();

    public Visual() {
        JFrame frame = new JFrame("Flashcard App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);

        JTabbedPane tabs = new JTabbedPane();

        // Add Card Tab
        JPanel addPanel = new JPanel(new GridLayout(7, 1));
        JTextField questionField = new JTextField();
        JTextField answerField = new JTextField();
        JTextField choicesField = new JTextField(); // for multiple choice
        JButton addButton = new JButton("Add Flashcard");
        JLabel statusLabel = new JLabel("");

        addPanel.add(new JLabel("Question:"));
        addPanel.add(questionField);
        addPanel.add(new JLabel("Answer:"));
        addPanel.add(answerField);
        addPanel.add(new JLabel("Choices (comma-separated, optional):"));
        addPanel.add(choicesField);
        addPanel.add(addButton);
        addPanel.add(statusLabel);

        addButton.addActionListener(e -> {
            String q = questionField.getText().trim();
            String a = answerField.getText().trim();
            String c = choicesField.getText().trim();
            if (!q.isEmpty() && !a.isEmpty()) {
                if (!c.isEmpty()) {
                    List<String> choices = Arrays.asList(c.split(","));
                    flashcardSet.addCard(new MultipleChoiceFlashcard(q, a, choices));
                } else {
                    flashcardSet.addCard(new Flashcard(q, a));
                }
                statusLabel.setText("Flashcard added!");
                questionField.setText("");
                answerField.setText("");
                choicesField.setText("");
            } else {
                statusLabel.setText("Fill at least question and answer.");
            }
        });

        // View Tab
        JPanel viewPanel = new JPanel(new BorderLayout());
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JButton refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(e -> {
            displayArea.setText("");
            for (Flashcard f : flashcardSet.getAllCards()) {
                displayArea.append(f.toString() + "\n");
            }
        });

        viewPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        viewPanel.add(refreshButton, BorderLayout.SOUTH);

        // Quiz Tab
        JPanel quizPanel = new JPanel(new BorderLayout());
        JLabel quizLabel = new JLabel("Click 'Next' to begin.");
        JTextField answerInput = new JTextField();
        JButton checkButton = new JButton("Check");
        JButton nextButton = new JButton("Next");
        JLabel feedbackLabel = new JLabel("");

        Flashcard[] current = new Flashcard[1];

        nextButton.addActionListener(e -> {
            if (flashcardSet.size() == 0) {
                quizLabel.setText("No flashcards to quiz.");
                return;
            }
            int i = (int)(Math.random() * flashcardSet.size());
            current[0] = flashcardSet.getCard(i);
            quizLabel.setText("Q: " + current[0].getQuestion());
            if (current[0] instanceof MultipleChoiceFlashcard mc) {
                quizLabel.setText("Q: " + mc.getQuestion() + " Options: " + String.join(", ", mc.getChoices()));
            }
            answerInput.setText("");
            feedbackLabel.setText("");
        });

        checkButton.addActionListener(e -> {
            if (current[0] == null) return;
            String ans = answerInput.getText().trim();
            if (ans.equalsIgnoreCase(current[0].getAnswer().trim())) {
                feedbackLabel.setText("Correct!");
            } else {
                feedbackLabel.setText("Wrong. Correct: " + current[0].getAnswer());
            }
        });

        JPanel quizBottom = new JPanel(new GridLayout(4, 1));
        quizBottom.add(answerInput);
        quizBottom.add(checkButton);
        quizBottom.add(nextButton);
        quizBottom.add(feedbackLabel);

        quizPanel.add(quizLabel, BorderLayout.NORTH);
        quizPanel.add(quizBottom, BorderLayout.CENTER);

        tabs.add("Add Card", addPanel);
        tabs.add("View Cards", viewPanel);
        tabs.add("Quiz Mode", quizPanel);

        frame.add(tabs);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Visual::new);
    }
}
